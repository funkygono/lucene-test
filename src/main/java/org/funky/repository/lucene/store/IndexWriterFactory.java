package org.funky.repository.lucene.store;

import org.apache.lucene.index.IndexWriter;

public interface IndexWriterFactory {

    IndexWriter createIndexWriter();

}
