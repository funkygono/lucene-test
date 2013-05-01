package org.funky.repository.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;
import org.funky.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LuceneRepository implements Repository {

    public static final String TITLE = "$title";
    public static final String ID = "$id";
    public static final String CONTENT = "$content";

    private final LuceneStore luceneStore;
    private final ContentSerializer contentSerializer;
    private final FieldBuilder fieldBuilder;

    @Autowired
    public LuceneRepository(LuceneStore luceneStore, ContentSerializer contentSerializer, FieldBuilder fieldBuilder) {
        this.luceneStore = luceneStore;
        this.contentSerializer = contentSerializer;
        this.fieldBuilder = fieldBuilder;
    }

    @Override
    public void store(Content content) {
        Document document = new Document();
        for (Value value : content.getValues()) {
            document.add(fieldBuilder.createFieldForValue(value));
        }
        document.add(newField(TITLE, content.getTitle()));
        document.add(newField(ID, content.getId()));
        document.add(newField(CONTENT, content));
        luceneStore.store(document);
    }

    private IndexableField newField(String property, Content content) {
        return new StoredField(property, contentSerializer.serialize(content));
    }

    private IndexableField newField(String property, String value) {
        return new TextField(property, value, Field.Store.NO);
    }

    @Override
    public List<Content> simpleSearch(String query) {
        List<Document> documents = luceneStore.simpleSearch(query);
        List<Content> result = new ArrayList<Content>(documents.size());
        for (Document document : documents) {
            result.add(contentSerializer.unserialize(document.getBinaryValue(CONTENT).bytes));
        }
        return result;
    }

    @Override
    public Content findById(String id) {
        return contentSerializer.unserialize(luceneStore.findById(id).getBinaryValue(CONTENT).bytes);
    }

}
