package com.denis.counter.controller;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.model.Counter;
import com.denis.counter.service.CounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CounterController.class)
class CounterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CounterService counterService;

    private static final int COUNTER_VALUE = 5;
    private static final String REQUEST_PARAM_NAME = "name";
    private static final String NAME = "sample";
    private static final List<String> NAMES = List.of("FIRST", "SECOND");

    @Test
    public void creatingNewCounterReturns201Status() throws Exception {
        var counter = new Counter(NAME, COUNTER_VALUE);
        given(counterService.createNewCounter(NAME)).willReturn(counter);
        this.mockMvc.perform(post("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.value", is(COUNTER_VALUE)))
                .andExpect(status().isCreated());
    }

    @Test
    public void creatingCounterSecondTimeReturns409Status() throws Exception {
        willThrow(new CounterAlreadyExistsException()).given(counterService).createNewCounter(anyString());
        this.mockMvc.perform(post("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(status().isConflict());
    }

    @Test
    public void incrementingExistingCounterValueReturns200() throws Exception {
        var counter = new Counter(NAME, COUNTER_VALUE + 1);
        given(counterService.incrementCounterValue(NAME)).willReturn(counter);
        this.mockMvc.perform(put("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.value", is(COUNTER_VALUE + 1)))
                .andExpect(status().isOk());
    }

    @Test
    public void incrementingNonExistingCounterValueReturns404() throws Exception {
        willThrow(new NoSuchCounterException()).given(counterService).incrementCounterValue(anyString());
        this.mockMvc.perform(put("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    public void gettingExistingCounterValueReturnsValueAnd200Status() throws Exception {
        given(counterService.getCounterValue(anyString())).willReturn(COUNTER_VALUE);
        this.mockMvc.perform(get("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(COUNTER_VALUE)));
    }

    @Test
    public void gettingNotExistingCounterValueReturns404Status() throws Exception {
        willThrow(new NoSuchCounterException()).given(counterService).getCounterValue(anyString());
        this.mockMvc.perform(get("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deletingExistingCounterValueReturns200() throws Exception {
        var counter = new Counter(NAME, COUNTER_VALUE);
        given(counterService.deleteCounter(NAME)).willReturn(counter);
        this.mockMvc.perform(delete("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.value", is(COUNTER_VALUE)))
                .andExpect(status().isOk());
    }

    @Test
    public void deletingNonExistingCounterValueReturns404() throws Exception {
        willThrow(new NoSuchCounterException()).given(counterService).deleteCounter(anyString());
        this.mockMvc.perform(delete("/counters").queryParam(REQUEST_PARAM_NAME, NAME))
                .andExpect(status().isNotFound());
    }

    @Test
    public void gettingAccumulativeValueReturnsValueAnd200Status() throws Exception {
        given(counterService.getAccumulativeValue()).willReturn(COUNTER_VALUE);
        this.mockMvc.perform(get("/counters/value")).andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(COUNTER_VALUE)));
    }

    @Test
    public void gettingCounterNameReturnsNamesAnd200Status() throws Exception {
        given(counterService.getCountersNames()).willReturn(NAMES);
        this.mockMvc.perform(get("/counters/names")).andExpect(status().isOk());
    }
}