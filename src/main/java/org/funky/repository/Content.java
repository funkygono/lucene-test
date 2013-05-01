package org.funky.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Content implements Serializable {

    private final List<Value> values;
    private final String id;
    private final String title;

    public Content(String id, String title, List<? extends Value> values) {
        this.values = Collections.unmodifiableList(values);
        this.id = id;
        this.title = title;
    }

    public Value get(String property) {
        for (Value value : values) {
            if (value.getName().equals(property)) {
                return value;
            }
        }
        return null;
    }

    public List<Value> getValues() {
        return values;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content = (Content) o;

        return !(id != null ? !id.equals(content.id) : content.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
