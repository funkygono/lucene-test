package org.funky.repository.lucene;

import org.apache.lucene.document.Document;
import org.funky.repository.Content;

/**
 * Builds a {@link Document} from a given {@link Content}.
 */
public interface DocumentBuilder {

    /**
     * Builds the Document for the given Content.
     * @param content the content
     * @return the document
     */
    Document build(Content content);

}
