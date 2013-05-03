package org.funky.repository.lucene.store;

import org.apache.lucene.index.IndexReader;

public interface IndexReaderFactory {

    IndexReader createIndexReader();

}
