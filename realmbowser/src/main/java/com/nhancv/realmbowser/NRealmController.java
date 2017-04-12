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
        //url: /api?schema=<table_name>
        //url: /api/query?where=<table_name>&field=<column_name>&value=<value>&action=<equalTo/....>

        if (uri.startsWith("/api")) {
            Map<String, String> params = session.getParms();
            String schema;

            String table = (params.containsKey("schema")) ? params.get("schema") : null;
            if (!TextUtils.isEmpty(table)) {
                schema = realmDiscovery.getSchema(table);
            } else {
                schema = realmDiscovery.getSchema();
            }
            return newFixedLengthResponse(NanoHTTPD.Response.Status.ACCEPTED, "application/json", schema);
        } else {
            return newFixedLengthResponse(uri + "<br>" + "Coming soon ...");
        }
    }

}
