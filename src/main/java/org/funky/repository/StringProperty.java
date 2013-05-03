package org.funky.repository;

/**
 * A property that holds String values.
 */
public class StringProperty extends BaseProperty<String> {

    /**
     * Create the property with the given name and the given value.
     * @param name the name of the property
     * @param value the value of the property
     */
    public StringProperty(String name, String value) {
        super(name, value);
    }

}
