package org.funky.repository.impl;

import org.funky.repository.Content;
import org.funky.repository.ContentSerializer;
import org.funky.repository.RepositoryException;
import org.springframework.stereotype.Component;

import java.io.*;

import static org.apache.commons.io.IOUtils.closeQuietly;

@Component
public class ContentSerializerImpl implements ContentSerializer {

    @Override
    public byte[] serialize(Content content) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            write(content, output);
            return output.toByteArray();
        } finally {
            closeQuietly(output);
        }
    }

    @Override
    public Content unserialize(byte[] data) {
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        try {
            return read(input);
        } finally {
            closeQuietly(input);
        }
    }

    private Content read(ByteArrayInputStream input) {
        ObjectInputStream objectStream = createObjectInputStream(input);
        try {
            return (Content) objectStream.readObject();
        } catch (Exception e) {
            throw new RepositoryException("Error while reading ObjectInputStream", e);
        } finally {
            closeQuietly(objectStream);
        }
    }

    private ObjectInputStream createObjectInputStream(ByteArrayInputStream input) {
        try {
            return new ObjectInputStream(input);
        } catch (IOException e) {
            throw new RepositoryException("Can not create ObjectInputStream", e);
        }
    }

    private void write(Content content, ByteArrayOutputStream output) {
        ObjectOutputStream objectOutput = createObjectOutputStream(output);
        try {
            objectOutput.writeObject(content);
        } catch (IOException e) {
            throw new RepositoryException("Error while writing into ObjectOutputStream", e);
        } finally {
            closeQuietly(objectOutput);
        }
    }

    private ObjectOutputStream createObjectOutputStream(ByteArrayOutputStream output) {
        try {
            return new ObjectOutputStream(output);
        } catch (IOException e) {
            throw new RepositoryException("Can not create ObjectOutputStream", e);
        }
    }

}
