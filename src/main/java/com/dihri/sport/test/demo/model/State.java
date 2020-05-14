package com.dihri.sport.test.demo.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class State {
    @Min(value = 0)
    @NotNull
    private Integer exp;
    private Integer level;

    public State(Integer exp, Integer level) {
        this.exp = exp;
        this.level = level;
    }

    public State() {}

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(exp, state.exp) &&
                Objects.equals(level, state.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp, level);
    }
}
