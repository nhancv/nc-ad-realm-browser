package com.nhancv.realmbowser.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nhancao on 4/12/17.
 */

public class DataResponse<T> {
    @SerializedName("data")
    private T data;

    public DataResponse(T data) {
        this.data = data;
    }
}
