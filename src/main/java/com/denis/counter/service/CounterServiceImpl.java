package com.denis.counter.service;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.model.Counter;
import com.denis.counter.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;

    @Override
    public Counter createNewCounter(String name) {
        var counter = new Counter(name, 0);
        if (counter.equals(counterRepository.addCounter(counter))) {
            throw new CounterAlreadyExistsException();
        }
        return counter;
    }

    @Override
    public Counter incrementCounterValue(String name) {
        return counterRepository.updateCounter(name)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public int getCounterValue(String name) {
        return counterRepository.getByName(name)
                .map(Counter::getValue)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public Counter deleteCounter(String name) {
        return counterRepository.deleteCounter(name)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public int getAccumulativeValue() {
        return counterRepository.getAllCounters().stream()
                .mapToInt(Counter::getValue)
                .reduce(0, Integer::sum);
    }

    @Override
    public List<String> getCountersNames() {
        return counterRepository.getAllCounters().stream()
                .map(Counter::getName)
                .collect(Collectors.toList());
    }
}
