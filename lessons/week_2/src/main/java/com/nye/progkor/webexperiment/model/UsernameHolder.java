package com.nye.progkor.webexperiment.model;

import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UsernameHolder {

    private static final String DEFAULT_NAME = "World";

    private String name = DEFAULT_NAME;

    public void reset() {
        this.name = DEFAULT_NAME;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsernameHolder that = (UsernameHolder) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "UsernameHolder{" +
                "name='" + name + '\'' +
                '}';
    }
}
