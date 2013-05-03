package org.funky.repository.lucene.document;

import org.apache.lucene.document.Document;
import org.funky.repository.Content;
import org.funky.repository.lucene.DocumentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentBuilderImpl implements DocumentBuilder {

    @Autowired List<FieldBuilder> fieldBuilders;

    @Override
    public Document build(Content content) {
        Document document = new Document();
        for (FieldBuilder fieldBuilder : fieldBuilders) {
            document.add(fieldBuilder.build(content));
        }
        return document;
    }

}
