package org.funky.repository.lucene.impl;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.funky.repository.StringValue;
import org.funky.repository.lucene.FieldFactory;
import org.springframework.stereotype.Component;

@Component
public class StringFieldFactory implements FieldFactory<StringValue> {

    @Override
    public Class<StringValue> getFactoryClass() {
        return StringValue.class;
    }

    @Override
    public Field create(StringValue value) {
        return new TextField(value.getName(), value.getValue(), Field.Store.NO);
    }

}
