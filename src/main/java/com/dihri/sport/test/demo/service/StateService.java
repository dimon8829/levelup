package com.dihri.sport.test.demo.service;

import com.dihri.sport.test.demo.model.State;
import com.dihri.sport.test.demo.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    @Autowired
    private Levelup levelup;
    @Autowired
    private StateRepository stateRepository;

    public State updateState(Integer id, Integer exp) {
        return stateRepository.insertState(id, (key,value) -> updateState(exp,value));
    }

    private State updateState(int exp, State state) {
        if(state==null) state = new State(0,1);
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
        return state;
    }



    public State getState(Integer id) {
        return stateRepository.getState(id);
    }
}
