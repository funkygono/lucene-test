package org.funky.repository;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Abstract implementation of the {@link Property}.
 * @param <T> the type of the value
 */
public abstract class BaseProperty<T extends Serializable> implements Property {

    private final String name;
    private final T value;

    public BaseProperty(@NotNull String name, @NotNull T value) {
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

        BaseProperty that = (BaseProperty) o;

        return name.equals(that.name) && value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

}
