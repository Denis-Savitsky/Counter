package com.denis.counter.repository;

import com.denis.counter.model.Counter;

import java.util.List;
import java.util.Optional;

public interface CounterRepository {

    Counter addCounter(Counter counter);
    Optional<Counter> getByName(String name);
    Optional<Counter> updateCounter(String name);
    Optional<Counter> deleteCounter(String name);
    List<Counter> getAllCounters();
}
