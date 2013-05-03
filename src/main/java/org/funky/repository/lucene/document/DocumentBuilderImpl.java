package org.funky.repository.lucene.document;

import org.apache.lucene.document.Document;
import org.funky.repository.Content;
import org.funky.repository.lucene.DocumentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class DocumentBuilderImpl implements DocumentBuilder {

    private final List<FieldBuilder> fieldBuilders;

    @Autowired
    public DocumentBuilderImpl(List<FieldBuilder> fieldBuilders) {
        this.fieldBuilders = Collections.unmodifiableList(fieldBuilders);
    }

    @Override
    public Document build(Content content) {
        Document document = new Document();
        for (FieldBuilder fieldBuilder : fieldBuilders) {
            document.add(fieldBuilder.build(content));
        }
        return document;
    }

}
