package org.funky.repository.lucene.impl;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.funky.repository.StringProperty;
import org.funky.repository.lucene.FieldFactory;
import org.springframework.stereotype.Component;

@Component
public class StringFieldFactory implements FieldFactory<StringProperty> {

    @Override
    public Class<StringProperty> getFactoryClass() {
        return StringProperty.class;
    }

    @Override
    public Field create(StringProperty value) {
        return new TextField(value.getName(), value.getValue(), Field.Store.NO);
    }

}
