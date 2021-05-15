package com.denis.counter.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Counter {

    private final String name;
    private final int value;

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Counter)) {
            return false;
        }

        var counter = (Counter) obj;
        return name.equals(counter.getName());
    }
}
