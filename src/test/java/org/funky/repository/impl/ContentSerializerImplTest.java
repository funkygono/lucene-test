package org.funky.repository.impl;

import org.funky.repository.Content;
import org.funky.repository.StringValue;
import org.funky.repository.Value;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContentSerializerImplTest {

    private ContentSerializerImpl serializer;

    @Before
    public void setUp() throws Exception {
        serializer = new ContentSerializerImpl();
    }

    @Test
    public void test__Serialize_And_Unserialize() {
        Content content = new Content("id", "title", Arrays.asList(new StringValue("key", "value")));
        byte[] data = serializer.serialize(content);
        Content result = serializer.unserialize(data);
        assertThat(result, is(content));
        assertThat(result.get("key"), is((Value) new StringValue("key", "value")));
    }

}
