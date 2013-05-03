package org.funky.repository;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Content implements Serializable {

    private final String id;
    private final String title;
    private final UUID stamp;
    private final List<Property> properties;

    public Content(String id, String title, UUID stamp, List<Property> properties) {
        this.properties = properties;
        this.id = id;
        this.stamp = stamp;
        this.title = title;
    }

    public Content(String id, String title, List<Property> properties) {
        this(id, title, UUID.randomUUID(), properties);
    }

    public Property get(String property) {
        for (Property value : properties) {
            if (value.getName().equals(property)) {
                return value;
            }
        }
        return null;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public UUID getStamp() {
        return stamp;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Content that = (Content) object;
        return new EqualsBuilder().append(this.id, that.id).append(this.stamp, that.stamp).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(stamp).toHashCode();
    }
}
