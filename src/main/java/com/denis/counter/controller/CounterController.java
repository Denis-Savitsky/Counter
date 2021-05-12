package com.denis.counter.controller;

import com.denis.counter.exceptions.CounterAlreadyExistsException;
import com.denis.counter.exceptions.NoSuchCounterException;
import com.denis.counter.service.CounterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@AllArgsConstructor
@RestController
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/counters")
    @ResponseStatus(HttpStatus.CREATED)
    public void createController(@RequestParam("name") String name) {
        try {
            counterService.createNewCounter(name);
        } catch (CounterAlreadyExistsException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Counter already exists");
        }
    }

    @PutMapping("/counters")
    public void incrementCounterValue(@RequestParam("name") String name) {
        try {
            counterService.incrementCounterValue(name);
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
    public void deleteCounter(@RequestParam("name") String name) {
        try {
            counterService.deleteCounter(name);
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
    public Set<String> getCountersNames() {
        return counterService.getCountersNames();
    }
}
