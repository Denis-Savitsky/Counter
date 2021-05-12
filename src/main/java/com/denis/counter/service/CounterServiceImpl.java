package com.denis.counter.service;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.model.Counter;
import com.denis.counter.repository.CounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

    private final CounterRepository counterRepository;

    @Override
    public void createNewCounter(String name) {
        var counter = new Counter(name, 0);
        if (counter.equals(counterRepository.addCounter(counter))) {
            throw new CounterAlreadyExistsException();
        }
    }

    @Override
    public void incrementCounterValue(String name) {
        counterRepository.updateCounter(name)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public int getCounterValue(String name) {
        return counterRepository.getByName(name)
                .map(Counter::getValue)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public void deleteCounter(String name) {
        counterRepository.deleteCounter(name)
                .orElseThrow(NoSuchCounterException::new);
    }

    @Override
    public int getAccumulativeValue() {
        return counterRepository.getAllCounters().stream()
                .mapToInt(Counter::getValue)
                .reduce(0, Integer::sum);
    }

    @Override
    public Set<String> getCountersNames() {
        return counterRepository.getAllCounters().stream()
                .map(Counter::getName)
                .collect(Collectors.toSet());
    }
}
