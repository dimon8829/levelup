package com.dihri.sport.test.demo.service;

import com.dihri.sport.test.demo.model.State;
import com.dihri.sport.test.demo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StateService {

    @Autowired
    private Levelup levelup;
    @Autowired
    private StateRepository stateRepository;

    public State updateState(Integer id, Integer exp) {
        AtomicReference<State> ref = stateRepository.getState(id);
        State updatedState = new State();
        boolean success = false;
        while (!success) {
            State expectedState = ref.get();
            updatedState.setLevel(expectedState.getLevel());
            updatedState.setExp(expectedState.getExp());
            int commonExp = exp;
            while(commonExp>0) {
                int currentLevel = updatedState.getLevel();
                int currentExp = updatedState.getExp();
                int maxExp = levelup.getMaxExp(currentLevel);
                boolean hasNextLevel = levelup.hasNext(currentLevel);

                if(!hasNextLevel || currentExp+commonExp<maxExp) {
                    updatedState.setExp(currentExp+commonExp);
                    updatedState.setLevel(currentLevel);
                    commonExp=0;
                } else {
                    currentLevel++;
                    updatedState.setExp(0);
                    updatedState.setLevel(currentLevel);
                    commonExp=commonExp-(maxExp-currentExp);
                }
            }
            success=ref.compareAndSet(expectedState,updatedState);
        }
        return updatedState;
    }



    public State getState(Integer id) {
        AtomicReference<State> ref = stateRepository.getState(id);
        return ref.get();
    }
}
