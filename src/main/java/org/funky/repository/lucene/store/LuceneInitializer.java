package org.funky.repository.lucene.store;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LuceneInitializer implements InitializingBean {

    private final IndexWriterFactory indexWriterFactory;

    @Autowired
    public LuceneInitializer(IndexWriterFactory indexWriterFactory) {
        this.indexWriterFactory = indexWriterFactory;
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
