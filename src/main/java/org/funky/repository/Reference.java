package org.funky.repository;

import java.io.Serializable;

public class Reference implements Serializable {

    private final String id;

    public Reference(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
