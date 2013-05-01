package org.funky.repository;

import java.io.Serializable;

public interface Value extends Serializable {

    String getName();
    Serializable getValue();

}
