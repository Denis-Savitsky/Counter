package com.denis.counter.controller;

import com.denis.counter.service.CounterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
public class CounterController {

    private final CounterService counterService;

    @PostMapping("/counters")
    public void createController(@RequestParam("name") String name) {
        counterService.createNewCounter(name);
    }

    @PutMapping("/counters")
    public void incrementCounterValue(@RequestParam("name") String name) {
        counterService.incrementCounterValue(name);
    }

    @GetMapping("/counters")
    public int getCounterValue(@RequestParam("name") String name) {
        return counterService.getCounterValue(name);
    }

    @DeleteMapping("/counters")
    public void deleteCounter(@RequestParam("name") String name) {
        counterService.deleteCounter(name);
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
