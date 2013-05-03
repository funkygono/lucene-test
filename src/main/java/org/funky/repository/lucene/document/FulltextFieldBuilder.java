package org.funky.repository.lucene.document;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.funky.repository.Content;
import org.funky.repository.Property;
import org.funky.repository.StringProperty;
import org.funky.repository.lucene.constants.Fields;
import org.springframework.stereotype.Component;

/**
 * Builds the field used for the fulltext search.
 */
@Component
public class FulltextFieldBuilder implements FieldBuilder {

    @Override
    public Field build(Content content) {
        return new TextField(Fields.TOKENS, extractAll(content), Field.Store.NO);
    }

    private String extractAll(Content content) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Property property : content.getProperties()) {
            if (property instanceof StringProperty) {
                stringBuilder.append(property.getValue());
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

}
