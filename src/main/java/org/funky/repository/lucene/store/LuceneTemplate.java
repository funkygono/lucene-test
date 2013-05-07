package org.funky.repository.lucene.store;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.funky.repository.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.IOUtils.closeQuietly;

@Component
public class LuceneTemplate {

    private static final Logger logger = LoggerFactory.getLogger(LuceneTemplate.class);
    private static final int MAX_DOC = 1000;
    private final IndexReaderFactory indexReaderFactory;
    private final IndexWriterFactory indexWriterFactory;

    @Autowired
    public LuceneTemplate(IndexReaderFactory indexReaderFactory, IndexWriterFactory indexWriterFactory) {
        this.indexReaderFactory = indexReaderFactory;
        this.indexWriterFactory = indexWriterFactory;
    }

    public Document find(Query query) {
        List<Document> documents = search(query);
        if (documents.isEmpty()) {
            throw new RepositoryException("No document found for query: " + query);
        }
        return documents.get(0);
    }

    public boolean exist(Query query) {
        return !search(query).isEmpty();
    }

    public List<Document> search(Query query) {
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
            throw new RepositoryException("Exception while executing query: " + query, e);
        } finally {
            closeQuietly(indexReader);
        }
    }

    public void addDocument(Document document) {
        logger.info("Adding document: {}", document);
        IndexWriter indexWriter = indexWriterFactory.createIndexWriter();
        try {
            indexWriter.addDocument(document);
        } catch (IOException e) {
            throw new RepositoryException("Can not add document: " + document, e);
        } finally {
            closeQuietly(indexWriter);
        }
    }

    public void updateDocument(Term term, Document document) {
        logger.info("Updating document: {}", document);
        IndexWriter indexWriter = indexWriterFactory.createIndexWriter();
        try {
            indexWriter.updateDocument(term, document);
        } catch (IOException e) {
            throw new RepositoryException("Can not add document: " + document, e);
        } finally {
            closeQuietly(indexWriter);
        }
    }

}
