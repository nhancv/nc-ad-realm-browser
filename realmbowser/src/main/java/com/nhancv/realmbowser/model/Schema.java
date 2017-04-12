package com.nhancv.realmbowser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhancao on 4/12/17.
 */

public class Schema {

    @SerializedName("name")
    private String name;
    @SerializedName("structures")
    private List<Structure> structures;

    public Schema(String name, List<Structure> structures) {
        this.name = name;
        this.structures = structures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public void setStructures(List<Structure> structures) {
        this.structures = structures;
    }

    public static class Structure {
        private String property;
        private String type;

        public Structure(String property, String type) {
            this.property = property;
            this.type = type;
        }
    }
}
