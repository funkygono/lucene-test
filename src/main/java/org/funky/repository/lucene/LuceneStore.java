package org.funky.repository.lucene;

import org.apache.lucene.document.Document;

import java.util.List;

/**
 * The LuceneStore is responsible to store documents and search on indexes.
 */
public interface LuceneStore {

    /**
     * Store the given document.
     * @param document the document to store
     */
    void store(Document document);

    /**
     * Do a simple search using the given query.
     * @param query the query
     * @return the documents found
     */
    List<Document> simpleSearch(String query);

    /**
     * Return the document that has the given ID, or throw an exception if no document with this ID is found.
     * @param id the ID
     * @return the document
     */
    Document findById(String id);
}
