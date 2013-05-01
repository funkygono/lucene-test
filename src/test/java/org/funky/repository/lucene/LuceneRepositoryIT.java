package org.funky.repository.lucene;

import org.funky.repository.Content;
import org.funky.repository.Repository;
import org.funky.repository.StringValue;
import org.funky.repository.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class LuceneRepositoryIT {

    @Autowired
    private Repository repository;

    @Test
    public void test__Store_And_Search() {
        Content content = createSimpleContent("id", "title", new StringValue("name", "funkygono"));
        repository.store(content);
        System.out.println(repository.simpleSearch("funkygono"));
        assertThat(repository.simpleSearch("funkygono"), is(Arrays.asList(content)));
    }

    private Content createSimpleContent(String id, String title, Value value) {
        return new Content(id, title, Arrays.asList(value));
    }

    @Test
    public void test__Store_And_Search_2_Times() {
        Content content1 = createSimpleContent("id1", "title1", new StringValue("name", "value"));
        Content content2 = createSimpleContent("id2", "title2", new StringValue("name", "value"));
        repository.store(content1);
        assertThat(repository.simpleSearch("value"), is(Arrays.asList(content1)));
        repository.store(content2);
        assertThat(repository.simpleSearch("value"), is(Arrays.asList(content1, content2)));
    }

    @Test
    public void test__Store_Search_Update_And_Search_Again() throws Exception {
        Content content = createSimpleContent("id", "title", new StringValue("name", "value1"));
        repository.store(content);
        content = createSimpleContent("id", "title", new StringValue("name", "value2"));
        repository.store(content);
        assertThat(repository.findById("id"), is(content));
    }
}
