package org.funky.repository.lucene.impl;

import org.apache.lucene.index.IndexWriter;

public interface IndexWriterFactory {

    IndexWriter createIndexWriter();

}
