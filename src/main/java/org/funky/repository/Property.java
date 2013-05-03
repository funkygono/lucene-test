package org.funky.repository;

import java.io.Serializable;

/**
 * Holds a content's property, which is composed of the name of the property and the value.
 */
public interface Property extends Serializable {

    /**
     * Return the name of the property.
     * @return the name
     */
    String getName();

    /**
     * Return the value of the property.
     * @return the value
     */
    Serializable getValue();

}
