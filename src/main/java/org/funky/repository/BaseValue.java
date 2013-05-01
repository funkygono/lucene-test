package org.funky.repository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public abstract class BaseValue<T extends Serializable> implements Value {

    private final String name;
    private final T value;

    public BaseValue(@NotNull String name, @NotNull T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseValue that = (BaseValue) o;

        return name.equals(that.name) && value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

}
