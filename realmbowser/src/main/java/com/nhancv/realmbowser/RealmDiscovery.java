package com.nhancv.realmbowser;

/**
 * Created by nhancao on 4/12/17.
 */

public interface RealmDiscovery {

    String getSchema();

    String getSchema(String tableName);

    String query(String where);

    String query(String where, String field, NRealmController.QUERY action, String value);

}
