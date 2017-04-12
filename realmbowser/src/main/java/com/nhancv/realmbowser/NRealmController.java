package com.nhancv.realmbowser;

import android.text.TextUtils;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmController implements RealmController {
    private static final String TAG = NRealmController.class.getSimpleName();

    private NRealmDiscovery realmDiscovery;

    public NRealmController(NRealmDiscovery realmDiscovery) {
        this.realmDiscovery = realmDiscovery;
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();

        //url: /api
        //url: /api?where=<table_name>
        //url: /api?where=<table_name>&all
        //url: /api?where=<table_name>&field=<column_name>&equal=<value>
        //url: /api?where=<table_name>&field=<column_name>&begin=<value>
        //url: /api?where=<table_name>&field=<column_name>&contains=<value>

        if (uri.startsWith("/api")) {
            Map<String, String> params = session.getParms();
            String response;

            String where = (params.containsKey("where")) ? params.get("where") : null;
            if (!TextUtils.isEmpty(where)) {
                boolean all = params.containsKey("all");
                if (all) {
                    //url: /api?where=<table_name>&all
                    response = realmDiscovery.query(where);
                } else {
                    //url: /api?where=<table_name>&field=<column_name>&action=<value>

                    //check field
                    String field = (params.containsKey("field")) ? params.get("field") : null;
                    //check action
                    String equal = (params.containsKey("equal")) ? params.get("equal") : null;
                    String begin = (params.containsKey("begin")) ? params.get("begin") : null;
                    String contains = (params.containsKey("contains")) ? params.get("contains") : null;
                    if (!TextUtils.isEmpty(field)) {
                        if (!TextUtils.isEmpty(equal)) {
                            response = realmDiscovery.query(where, field, QUERY.EQUAL, equal);
                        } else if (!TextUtils.isEmpty(begin)) {
                            response = realmDiscovery.query(where, field, QUERY.BEGIN, begin);
                        } else if (!TextUtils.isEmpty(contains)) {
                            response = realmDiscovery.query(where, field, QUERY.CONTAINS, contains);
                        } else {
                            response = realmDiscovery.getSchema(where);
                        }
                    } else {
                        response = realmDiscovery.getSchema(where);
                    }
                }
            } else {
                response = realmDiscovery.getSchema();
            }
            return newFixedLengthResponse(NanoHTTPD.Response.Status.ACCEPTED, "application/json", response);
        } else {
            return newFixedLengthResponse(uri + "<br>" + "Coming soon ...");
        }
    }

    public enum QUERY {
        EQUAL,
        BEGIN,
        CONTAINS
    }

}
