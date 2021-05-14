package com.denis.counter.service;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.model.Counter;
import com.denis.counter.repository.CounterRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CounterServiceTest {

    private CounterRepository counterRepository;
    private CounterService counterService;

    private static final int COUNTER_VALUE = 0;
    private static final String NAME = "sample";
    private static final String NAME_TWO = "another";

    @BeforeEach
    void setUp() {
        counterRepository = mock(CounterRepository.class);
        counterService = new CounterServiceImpl(counterRepository);
    }

    @Test
    void createsNewCounterReturnsNewCounterWithValueZero() {
        given(counterRepository.addCounter(any(Counter.class))).willReturn(null);
        var counter = new Counter(NAME, COUNTER_VALUE);
        assertEquals(counter, counterService.createNewCounter(NAME));
        verify(counterRepository).addCounter(counter);
    }

    @Test
    void createsNewCounterThrowsExceptionIfSuchCounterAlreadyExists() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        given(counterRepository.addCounter(counter)).willReturn(counter);
        assertThrows(CounterAlreadyExistsException.class,
                () -> counterService.createNewCounter(NAME));
        verify(counterRepository).addCounter(counter);
    }

    @Test
    void incrementCounterValueReturnsCounterWithIncrementedValue() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        var counterIncremented = new Counter(NAME, COUNTER_VALUE + 1);
        given(counterRepository.updateCounter(counter.getName())).willReturn(Optional.of(counterIncremented));
        val COUNTER_TO_COMPARE = counterService.incrementCounterValue(counter.getName());
        assertEquals(counter.getName(), COUNTER_TO_COMPARE.getName());
        assertEquals(counter.getValue() + 1, COUNTER_TO_COMPARE.getValue());
        verify(counterRepository).updateCounter(counter.getName());
    }

    @Test
    void incrementCounterValueThrowsExceptionIfSuchCounterNotExists() {
        given(counterRepository.updateCounter(NAME)).willReturn(Optional.ofNullable(null));
        assertThrows(NoSuchCounterException.class,
                () -> counterService.incrementCounterValue(NAME));
        verify(counterRepository).updateCounter(NAME);
    }

    @Test
    void getCounterValueReturnsExistingCounterValue() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        given(counterRepository.getByName(counter.getName())).willReturn(Optional.of(counter));
        assertEquals(counter.getValue(), counterService.getCounterValue(counter.getName()));
        verify(counterRepository).getByName(counter.getName());
    }

    @Test
    void getCounterValueThrowsExceptionIfSuchCounterNotExists() {
        given(counterRepository.getByName(NAME)).willReturn(Optional.ofNullable(null));
        assertThrows(NoSuchCounterException.class,
                () -> counterService.getCounterValue(NAME));
        verify(counterRepository).getByName(NAME);
    }

    @Test
    void deleteCounterValueReturnsRemovedCounter() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        given(counterRepository.deleteCounter(counter.getName())).willReturn(Optional.of(counter));
        assertEquals(counter, counterService.deleteCounter(counter.getName()));
        verify(counterRepository).deleteCounter(counter.getName());
    }

    @Test
    void deleteCounterThrowsExceptionIfSuchCounterNotExists() {
        given(counterRepository.deleteCounter(NAME)).willReturn(Optional.ofNullable(null));
        assertThrows(NoSuchCounterException.class,
                () -> counterService.deleteCounter(NAME));
        verify(counterRepository).deleteCounter(NAME);
    }

    @Test
    void getAccumulativeValueReturnZeroIfNoCounters() {
        given(counterRepository.getAllCounters()).willReturn(List.of());
        assertEquals(counterService.getAccumulativeValue(), 0);
        verify(counterRepository).getAllCounters();
    }

    @Test
    void getAccumulativeValueReturnsSumOfCounterValues() {
        var counter = new Counter(NAME, 2);
        var counterTwo = new Counter(NAME_TWO, 5);
        given(counterRepository.getAllCounters()).willReturn(List.of(counterTwo, counter));
        assertEquals(counterService.getAccumulativeValue(), 7);
        verify(counterRepository).getAllCounters();
    }

    @Test
    void getCountersNamesReturnsEmptyListIfNoCounters() {
        given(counterRepository.getAllCounters()).willReturn(List.of());
        assertEquals(counterService.getCountersNames(), Collections.emptyList());
        verify(counterRepository).getAllCounters();
    }

    @Test
    void getCountersNamesReturnsListOfCountersNames() {
        var counter = new Counter(NAME, 2);
        var counterTwo = new Counter(NAME_TWO, 5);
        given(counterRepository.getAllCounters()).willReturn(List.of(counter, counterTwo));
        assertTrue(counterService.getCountersNames().containsAll(List.of(NAME, NAME_TWO)));
        verify(counterRepository).getAllCounters();
    }













}