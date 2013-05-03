package org.funky.repository.lucene.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.funky.repository.RepositoryException;
import org.funky.repository.lucene.LuceneRepository;
import org.funky.repository.lucene.LuceneStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.IOUtils.closeQuietly;

@Component
public class LuceneStoreImpl implements LuceneStore, InitializingBean {

    private static final int MAX_DOC = 1000;
    private final IndexWriterFactory indexWriterFactory;
    private final IndexReaderFactory indexReaderFactory;

    @Autowired
    public LuceneStoreImpl(IndexWriterFactory indexWriterFactory, IndexReaderFactory indexReaderFactory) {
        this.indexWriterFactory = indexWriterFactory;
        this.indexReaderFactory = indexReaderFactory;
    }

    @Override
    public void store(Document document) {
        IndexWriter indexWriter = indexWriterFactory.createIndexWriter();
        try {
            String id = document.get(LuceneRepository.ID);
            if (exists(id)) {
                indexWriter.updateDocument(createByIdTerm(id), document);
            } else {
                indexWriter.addDocument(document);
            }
        } catch (IOException e) {
            throw new RepositoryException("Can not add document: " + document, e);
        } finally {
            closeQuietly(indexWriter);
        }
    }

    @Override
    public List<Document> simpleSearch(String text) {
        return search(new TermQuery(new Term("name", text)));
    }

    @Override
    public Document findById(String id) {
        List<Document> documents = search(queryById(id));
        if (documents.size() > 1) {
            throw new RepositoryException("Found several document with ID: " + id);
        }
        if (documents.isEmpty()) {
            throw new RepositoryException("Found not document with ID: " + id);
        } else {
            return documents.get(0);
        }
    }

    private TermQuery queryById(String id) {
        return new TermQuery(createByIdTerm(id));
    }

    private Term createByIdTerm(String id) {
        return new Term(LuceneRepository.ID, id);
    }

    private boolean exists(String id) {
        return !search(queryById(id)).isEmpty();
    }

    private List<Document> search(Query query) {
        IndexReader indexReader = indexReaderFactory.createIndexReader();
        try {
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            TopDocs topDocs = indexSearcher.search(query, MAX_DOC);
            List<Document> result = new ArrayList<Document>(topDocs.scoreDocs.length);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                result.add(indexReader.document(scoreDoc.doc));
            }
            return result;
        } catch (IOException e) {
            throw new RepositoryException("Exception while searching query: '" + query + "'", e);
        } finally {
            closeQuietly(indexReader);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        IndexWriter indexWriter = indexWriterFactory.createIndexWriter();
        try {
            indexWriter.addDocument(new Document());
        } finally {
            indexWriter.close();
        }
    }
}
