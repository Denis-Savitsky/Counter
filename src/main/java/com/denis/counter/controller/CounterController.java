package com.denis.counter.controller;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.model.Counter;
import com.denis.counter.service.CounterService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@Scope("request")
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/counters")
    @ResponseStatus(HttpStatus.CREATED)
    public Counter createController(@RequestParam("name") String name) {
        try {
            return counterService.createNewCounter(name);
        } catch (CounterAlreadyExistsException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Counter already exists");
        }
    }

    @PutMapping("/counters")
    public Counter incrementCounterValue(@RequestParam("name") String name) {
        try {
            return counterService.incrementCounterValue(name);
        } catch (NoSuchCounterException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Counter not found");
        }
    }

    @GetMapping("/counters")
    public int getCounterValue(@RequestParam("name") String name) {
        try {
            return counterService.getCounterValue(name);
        } catch (NoSuchCounterException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Counter not found");
        }
    }

    @DeleteMapping("/counters")
    public Counter deleteCounter(@RequestParam("name") String name) {
        try {
            return counterService.deleteCounter(name);
        } catch (NoSuchCounterException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Counter not found");
        }
    }

    @GetMapping("/counters/value")
    public int getAccumulativeValue() {
        return counterService.getAccumulativeValue();
    }

    @GetMapping("/counters/names")
    public List<String> getCountersNames() {
        return counterService.getCountersNames();
    }
}
