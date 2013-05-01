package org.funky.repository.lucene;

import org.apache.lucene.document.Field;
import org.funky.repository.Value;

public interface FieldFactory<T extends Value> {

    Class<T> getFactoryClass();

    Field create(T value);

}
