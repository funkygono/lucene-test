package org.funky.repository.lucene.impl;

import org.apache.lucene.index.IndexReader;

public interface IndexReaderFactory {

    IndexReader createIndexReader();

}
