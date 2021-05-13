package com.denis.counter.service;

import com.denis.counter.model.Counter;

import java.util.List;
import java.util.Set;

public interface CounterService {
    Counter createNewCounter(String name);
    Counter incrementCounterValue(String name);
    int getCounterValue(String name);
    Counter deleteCounter(String name);
    int getAccumulativeValue();
    List<String> getCountersNames();
}
