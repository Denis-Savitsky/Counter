package com.denis.counter.repository;

import com.denis.counter.model.Counter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapCounterRepository implements CounterRepository {

    private final ConcurrentHashMap<String, Counter> counters;

    public MapCounterRepository() {
        counters = new ConcurrentHashMap<>();
    }

    @Override
    public Counter addCounter(Counter counter) {
        return counters.putIfAbsent(counter.getName(), counter);
    }

    @Override
    public Optional<Counter> getByName(String name) {
        return Optional.ofNullable(counters.get(name));
    }

    @Override
    public Optional<Counter> updateCounter(String name) {
        return Optional.ofNullable(counters.computeIfPresent(name,
                (key, value) -> new Counter(name, value.getValue() + 1)));
    }

    @Override
    public Optional<Counter> deleteCounter(String name) {
        return Optional.ofNullable(counters.remove(name));
    }

    @Override
    public List<Counter> getAllCounters() {
        return new ArrayList<>(counters.values());
    }


}
