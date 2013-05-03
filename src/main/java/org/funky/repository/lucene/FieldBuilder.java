package org.funky.repository.lucene;

import org.apache.lucene.document.Field;
import org.funky.repository.RepositoryException;
import org.funky.repository.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FieldBuilder {

    private final Map<Class<Property>, FieldFactory<Property>> fieldFactories;

    @Autowired
    public FieldBuilder(List<FieldFactory<Property>> fieldFactories) {
        this.fieldFactories = toMap(fieldFactories);
    }

    private Map<Class<Property>, FieldFactory<Property>> toMap(List<FieldFactory<Property>> fieldFactories) {
        Map<Class<Property>, FieldFactory<Property>> map = newMap(fieldFactories);
        for (FieldFactory<Property> fieldFactory : fieldFactories) {
            map.put(fieldFactory.getFactoryClass(), fieldFactory);
        }
        return Collections.unmodifiableMap(map);
    }

    private HashMap<Class<Property>, FieldFactory<Property>> newMap(List<FieldFactory<Property>> fieldFactories) {
        return new HashMap<Class<Property>, FieldFactory<Property>>(fieldFactories.size());
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public Field createFieldForValue(Property property) {
        FieldFactory<Property> fieldFactory = fieldFactories.get(property.getClass());
        if (fieldFactory == null) {
            throw new RepositoryException("No FieldFactory for class: " + property.getClass());
        }
        return fieldFactory.create(property);
    }
}
