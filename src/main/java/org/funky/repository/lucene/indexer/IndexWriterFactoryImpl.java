package org.funky.repository.lucene.indexer;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.funky.repository.RepositoryException;
import org.funky.repository.lucene.store.IndexWriterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IndexWriterFactoryImpl implements IndexWriterFactory {

    private final Directory directory;
    private final IndexWriterConfig indexWriterConfig;

    @Autowired
    public IndexWriterFactoryImpl(Directory directory, IndexWriterConfig indexWriterConfig) {
        this.directory = directory;
        this.indexWriterConfig = indexWriterConfig;
    }

    @Override
    public IndexWriter createIndexWriter() {
        try {
            return new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            throw new RepositoryException("Can not create index", e);
        }
    }

}
