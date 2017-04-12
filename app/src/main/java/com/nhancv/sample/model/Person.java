package com.nhancv.sample.model;

import io.realm.RealmObject;

/**
 * Created by nhancao on 4/11/17.
 */

public class Person extends RealmObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
