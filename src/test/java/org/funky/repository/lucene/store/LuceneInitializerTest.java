package org.funky.repository.lucene.store;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

public class LuceneInitializerTest {

    private IndexWriterFactory indexWriterFactory;
    private LuceneInitializer luceneInitializer;
    private IMocksControl mocks;

    @Before
    public void setUp() {
        mocks = createControl();
        indexWriterFactory = mocks.createMock(IndexWriterFactory.class);
        luceneInitializer = new LuceneInitializer(indexWriterFactory);
    }

    @Test
    public void testAfterPropertiesSet() throws Exception {
        IndexWriter indexWriter = mocks.createMock(IndexWriter.class);

        expect(indexWriterFactory.createIndexWriter()).andReturn(indexWriter);
        indexWriter.addDocument(isA(Document.class));
        expectLastCall();
        indexWriter.close();
        expectLastCall();

        mocks.replay();
        luceneInitializer.afterPropertiesSet();
        mocks.verify();
    }

}
