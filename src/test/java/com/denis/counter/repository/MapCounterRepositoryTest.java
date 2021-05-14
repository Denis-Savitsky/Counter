package com.denis.counter.repository;

import com.denis.counter.model.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MapCounterRepositoryTest {

    private MapCounterRepository mapCounterRepository;

    private static final int COUNTER_VALUE = 0;
    private static final String NAME = "sample";
    private static final String NAME_TWO = "another";

    @BeforeEach
    public void setUp() {
        mapCounterRepository = new MapCounterRepository();
    }

    @Test
    public void addingNewCounterIncreasesTheNumberOfCounters() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        assertEquals(1, mapCounterRepository.getAllCounters().size());
    }

    @Test
    public void addingTheSameCounterTwiceDoesNotIncreaseTheNumberOfCounters() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        mapCounterRepository.addCounter(counter);
        assertEquals(1, mapCounterRepository.getAllCounters().size());
    }

    @Test
    public void gettingByNameReturnsExistingCounter() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        assertThat(mapCounterRepository.getByName(counter.getName()))
                .isNotEmpty()
                .hasValue(counter);
    }

    @Test
    public void gettingByNameNonExistingCounterReturnsEmpty() {
        assertThat(mapCounterRepository.getByName(NAME))
                .isEmpty();
    }

    @Test
    public void updatingCounterIncrementsTheCounterValue() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        assertThat(mapCounterRepository.updateCounter(counter.getName()))
                .isNotEmpty()
                .hasValue(new Counter(NAME, COUNTER_VALUE + 1));
    }

    @Test
    public void updatingNotExistingCounterReturnsEmpty() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        assertThat(mapCounterRepository.updateCounter(counter.getName())).isEmpty();
    }

    @Test
    public void deletingCounterRemovesAndReturnsCounter() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        assertThat(mapCounterRepository.deleteCounter(counter.getName()))
                .isNotEmpty()
                .hasValue(counter);
        assertThat(mapCounterRepository.getAllCounters().size()).isEqualTo(COUNTER_VALUE);
    }

    @Test
    public void deletingNonExistingCounterReturnsEmpty() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        assertThat(mapCounterRepository.deleteCounter(counter.getName())).isEmpty();
    }

    @Test
    public void getAllCountersReturnsTheListOfExistingCounters() {
        var counter = new Counter(NAME, COUNTER_VALUE);
        var counterTwo = new Counter(NAME_TWO, COUNTER_VALUE);
        mapCounterRepository.addCounter(counter);
        mapCounterRepository.addCounter(counterTwo);
        assertThat(mapCounterRepository.getAllCounters()).isNotEmpty()
                .hasSize(2)
                .contains(counter, counterTwo);
    }
}