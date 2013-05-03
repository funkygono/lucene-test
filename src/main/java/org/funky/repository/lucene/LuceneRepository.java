package org.funky.repository.lucene;

import org.apache.lucene.document.Document;
import org.funky.repository.Content;
import org.funky.repository.ContentSerializer;
import org.funky.repository.Repository;
import org.funky.repository.lucene.constants.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link Repository} using Lucene to store the search indexes and the content itself.
 */
@Component
public class LuceneRepository implements Repository {

    private final LuceneStore luceneStore;
    private final ContentSerializer contentSerializer;
    private final DocumentBuilder documentBuilder;

    @Autowired
    public LuceneRepository(LuceneStore luceneStore, ContentSerializer contentSerializer, DocumentBuilder documentBuilder) {
        this.luceneStore = luceneStore;
        this.contentSerializer = contentSerializer;
        this.documentBuilder = documentBuilder;
    }

    @Override
    public void store(Content content) {
        luceneStore.store(documentBuilder.build(content));
    }

    @Override
    public List<Content> simpleSearch(String query) {
        List<Document> documents = luceneStore.simpleSearch(query);
        List<Content> result = new ArrayList<Content>(documents.size());
        for (Document document : documents) {
            result.add(contentSerializer.unserialize(document.getBinaryValue(Fields.CONTENT).bytes));
        }
        return result;
    }

    @Override
    public Content findById(String id) {
        return contentSerializer.unserialize(luceneStore.findById(id).getBinaryValue(Fields.CONTENT).bytes);
    }

}
