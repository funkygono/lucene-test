package org.funky.repository;

import java.util.List;

/**
 * The repository is responsible to store content, and find them.
 */
public interface Repository {

    /**
     * Store the given content in the repository.
     * @param content the content to store
     */
    void store(Content content);

    /**
     * Perform a simple search query from the given query and return the found contents.
     * @param query the query
     * @return the contents
     */
    List<Content> simpleSearch(String query);

    Content findById(String id);
}
