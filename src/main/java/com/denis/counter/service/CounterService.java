package com.denis.counter.service;

import java.util.Set;

public interface CounterService {
    void createNewCounter(String name);
    void incrementCounterValue(String name);
    int getCounterValue(String name);
    void deleteCounter(String name);
    int getAccumulativeValue();
    Set<String> getCountersNames();
}
