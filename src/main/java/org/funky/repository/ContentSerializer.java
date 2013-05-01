package org.funky.repository;

public interface ContentSerializer {

    byte[] serialize(Content content);

    Content unserialize(byte[] data);

}
