package org.funky.repository.lucene;

import org.apache.lucene.document.Field;
import org.funky.repository.Property;

public interface FieldFactory<T extends Property> {

    Class<T> getFactoryClass();

    Field create(T value);

}
