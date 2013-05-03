package org.funky.repository;

/**
 * A ContentSerializer is responsible to serialize and unserialize contents.
 */
public interface ContentSerializer {

    /**
     * Serialize the given content as a byte array.
     * @param content the content to serialize
     * @return the serialized content
     */
    byte[] serialize(Content content);

    /**
     * Unserialize the given byte array as a content.
     * @param data the byte array
     * @return the unserialized content
     */
    Content unserialize(byte[] data);

}
