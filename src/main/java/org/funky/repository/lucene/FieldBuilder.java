package org.funky.repository.lucene;

import org.apache.lucene.document.Field;
import org.funky.repository.RepositoryException;
import org.funky.repository.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FieldBuilder {

    private final Map<Class<? extends Value>, FieldFactory<Value>> fieldFactories;

    @Autowired
    public FieldBuilder(List<FieldFactory<Value>> fieldFactories) {
        this.fieldFactories = toMap(fieldFactories);
    }

    private Map<Class<? extends Value>, FieldFactory<Value>> toMap(List<FieldFactory<Value>> fieldFactories) {
        Map<Class<? extends Value>, FieldFactory<Value>> map =
                new HashMap<Class<? extends Value>, FieldFactory<Value>>(fieldFactories.size());
        for (FieldFactory<Value> fieldFactory : fieldFactories) {
            map.put(fieldFactory.getFactoryClass(), fieldFactory);
        }
        return Collections.unmodifiableMap(map);
    }

    public Field createFieldForValue(Value value) {
        FieldFactory<Value> fieldFactory = fieldFactories.get(value.getClass());
        if (fieldFactory == null) {
            throw new RepositoryException("No FieldFactory for class: " + value.getClass());
        }
        return fieldFactory.create(value);
    }
}
