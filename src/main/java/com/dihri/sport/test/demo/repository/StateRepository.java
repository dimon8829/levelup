package com.dihri.sport.test.demo.repository;

import com.dihri.sport.test.demo.model.State;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

@Repository
public class StateRepository {

    private ConcurrentMap<Integer, AtomicReference<State>> cache = new ConcurrentHashMap<>();

    public AtomicReference<State> getState(Integer id) {
        cache.putIfAbsent(id,new AtomicReference<>(new State(0,1)));
        return cache.get(id);
    }
}
