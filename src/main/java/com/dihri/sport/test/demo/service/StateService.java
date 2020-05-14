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
            updateState(exp,updatedState);
            success=ref.compareAndSet(expectedState,updatedState);
        }
        return updatedState;
    }

    private void updateState(int exp, State state) {
        while(exp>0) {
            int currentLevel = state.getLevel();
            int currentExp = state.getExp();
            int maxExp = levelup.getMaxExp(currentLevel);
            boolean hasNextLevel = levelup.hasNext(currentLevel);

            if(!hasNextLevel || currentExp+exp<maxExp) {
                state.setExp(currentExp+exp);
                state.setLevel(currentLevel);
                exp=0;
            } else {
                currentLevel++;
                state.setExp(0);
                state.setLevel(currentLevel);
                exp=exp-(maxExp-currentExp);
            }
        }
    }



    public State getState(Integer id) {
        AtomicReference<State> ref = stateRepository.getState(id);
        return ref.get();
    }
}
