package com.nhancv.realmbowser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhancao on 4/12/17.
 */

public class SchemaData {

    @SerializedName("columns")
    private List<String> columns;
    @SerializedName("rows")
    private List<List<Object>> rows;
    @SerializedName("total")
    private int total;

    public SchemaData(List<String> columns, List<List<Object>> rows, int total) {
        this.columns = columns;
        this.rows = rows;
        this.total = total;
    }
}
