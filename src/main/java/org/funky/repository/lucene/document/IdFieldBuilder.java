package org.funky.repository.lucene.document;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.funky.repository.Content;
import org.funky.repository.lucene.constants.Fields;
import org.springframework.stereotype.Component;

/**
 * Builds the ID field.
 */
@Component
public class IdFieldBuilder implements FieldBuilder {

    @Override
    public Field build(Content content) {
        return new TextField(Fields.ID, content.getId(), Field.Store.NO);
    }

}
