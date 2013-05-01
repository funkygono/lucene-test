package org.funky.repository.lucene.impl;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.funky.repository.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IndexReaderFactoryImpl implements IndexReaderFactory {

    private final Directory directory;

    @Autowired
    public IndexReaderFactoryImpl(Directory directory) {
        this.directory = directory;
    }

    @Override
    public IndexReader createIndexReader() {
        try {
            return DirectoryReader.open(directory);
        } catch (IOException e) {
            throw new RepositoryException("Can not create index reader", e);
        }
    }

}
