package com.dihri.sport.test.demo.repository;

import com.dihri.sport.test.demo.model.State;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

@Repository
public class StateRepository {

    private ConcurrentMap<Integer,State> cache = new ConcurrentHashMap<>();

    public State insertState(Integer id, BiFunction<Integer,State,State> calculateState) {
        return cache.compute(id,calculateState);
    }

    public State getState(Integer id) {
        return cache.getOrDefault(id,new State(0,1));
    }
}
