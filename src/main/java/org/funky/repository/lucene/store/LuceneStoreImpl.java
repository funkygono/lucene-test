package org.funky.repository.lucene.store;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.funky.repository.lucene.LuceneStore;
import org.funky.repository.lucene.constants.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LuceneStoreImpl implements LuceneStore {

    private final LuceneTemplate luceneTemplate;

    @Autowired
    public LuceneStoreImpl(LuceneTemplate luceneTemplate) {
        this.luceneTemplate = luceneTemplate;
    }

    @Override
    public void store(Document document) {
        Term term = new Term(Fields.ID, document.get(Fields.ID));
        if (luceneTemplate.exist(new TermQuery(term))) {
            luceneTemplate.updateDocument(term, document);
        } else {
            luceneTemplate.addDocument(document);
        }
    }

    @Override
    public List<Document> simpleSearch(String text) {
        return search(new TermQuery(new Term(Fields.TOKENS, text)));
    }

    @Override
    public Document findById(String id) {
        return luceneTemplate.find(new TermQuery(new Term(Fields.ID, id)));
    }

    private List<Document> search(Query query) {
        return luceneTemplate.search(query);
    }

}
