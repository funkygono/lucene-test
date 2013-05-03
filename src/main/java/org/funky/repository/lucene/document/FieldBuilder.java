package org.funky.repository.lucene.document;

import org.apache.lucene.document.Field;
import org.funky.repository.Content;

/**
 * Builds a {@link Field} from a {@link Content}.
 */
public interface FieldBuilder {

    /**
     * Builds the Field for the given Content.
     * @param content the content
     * @return the Field
     */
    Field build(Content content);

}
