package org.funky.repository.lucene.store;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.funky.repository.lucene.constants.Fields;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

public class LuceneStoreImplTest {

    private IMocksControl mocks;
    private LuceneTemplate luceneTemplate;
    private LuceneStoreImpl luceneStore;

    @Before
    public void setUp() throws Exception {
        mocks = EasyMock.createControl();
        luceneTemplate = mocks.createMock(LuceneTemplate.class);
        luceneStore = new LuceneStoreImpl(luceneTemplate);
    }

    @Test
    public void test_Store_For_New_Document() throws Exception {
        Document document = new Document();
        document.add(new TextField(Fields.ID, "my_id", Field.Store.NO));
        Capture<Query> query = new Capture<Query>();

        expect(luceneTemplate.exist(capture(query))).andReturn(false);
        luceneTemplate.addDocument(document);
        expectLastCall();

        mocks.replay();
        luceneStore.store(document);
        mocks.verify();

        assertThat(query.getValue(), instanceOf(TermQuery.class));
        assertThat(((TermQuery)query.getValue()).getTerm(), is(new Term(Fields.ID, "my_id")));
    }

    @Test
    public void test_Store_For_Existing_Document() throws Exception {
        Document document = new Document();
        document.add(new TextField(Fields.ID, "my_id", Field.Store.NO));
        Capture<Query> query = new Capture<Query>();

        expect(luceneTemplate.exist(capture(query))).andReturn(true);
        luceneTemplate.updateDocument(new Term(Fields.ID, "my_id"), document);
        expectLastCall();

        mocks.replay();
        luceneStore.store(document);
        mocks.verify();

        assertThat(query.getValue(), instanceOf(TermQuery.class));
        assertThat(((TermQuery)query.getValue()).getTerm(), is(new Term(Fields.ID, "my_id")));
    }

    @Test
    public void testSimpleSearch() throws Exception {
        Capture<Query> query = new Capture<Query>();
        List<Document> documents = Arrays.asList(new Document(), new Document());

        expect(luceneTemplate.search(capture(query))).andReturn(documents);

        mocks.replay();
        assertThat(luceneStore.simpleSearch("my search"), is(documents));
        mocks.verify();

        assertThat(query.getValue(), instanceOf(TermQuery.class));
        assertThat(((TermQuery)query.getValue()).getTerm(), is(new Term(Fields.TOKENS, "my search")));
    }

    @Test
    public void testFindById() throws Exception {
        Document document = new Document();
        Capture<Query> query = new Capture<Query>();

        expect(luceneTemplate.find(capture(query))).andReturn(document);

        mocks.replay();
        assertThat(luceneStore.findById("my_id"), is(document));
        mocks.verify();

        assertThat(query.getValue(), instanceOf(TermQuery.class));
        assertThat(((TermQuery)query.getValue()).getTerm(), is(new Term(Fields.ID, "my_id")));
    }
}
