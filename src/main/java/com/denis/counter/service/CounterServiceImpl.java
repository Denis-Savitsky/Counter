package com.denis.counter.service;

import com.denis.counter.model.Counter;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CounterServiceImpl implements CounterService{

    private final ConcurrentHashMap<String, Counter> counters;

    public CounterServiceImpl() {
        counters = new ConcurrentHashMap<>();
    }

    @Override
    public void createNewCounter(String name) {
        counters.putIfAbsent(name, new Counter(name, 0));
    }

    @Override
    public void incrementCounterValue(String name) {
        counters.computeIfPresent(name, (key, val) -> new Counter(key, val.getValue() + 1));
    }

    @Override
    public int getCounterValue(String name) {
        return counters.get(name).getValue();
    }

    @Override
    public void deleteCounter(String name) {
        counters.remove(name);
    }

    @Override
    public int getAccumulativeValue() {
        return counters.values()
                .stream()
                .mapToInt(Counter::getValue)
                .reduce(0, Integer::sum);
    }

    @Override
    public Set<String> getCountersNames() {
        return counters.keySet();
    }
}
