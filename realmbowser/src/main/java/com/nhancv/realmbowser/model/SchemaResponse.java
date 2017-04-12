package com.nhancv.realmbowser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhancao on 4/12/17.
 */

public class SchemaResponse {
    @SerializedName("schemas")
    private List<Schema> schemas;

    public SchemaResponse(List<Schema> schemas) {
        this.schemas = schemas;
    }
}
