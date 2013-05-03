package org.funky.repository.lucene.document;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.funky.repository.Content;
import org.funky.repository.ContentSerializer;
import org.funky.repository.lucene.constants.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Builds the field that stores the content itself.
 */
@Component
public class ContentFieldBuilder implements FieldBuilder {

    private final ContentSerializer contentSerializer;

    @Autowired
    public ContentFieldBuilder(ContentSerializer contentSerializer) {
        this.contentSerializer = contentSerializer;
    }

    @Override
    public Field build(Content content) {
        return new StoredField(Fields.CONTENT, contentSerializer.serialize(content));
    }

}
