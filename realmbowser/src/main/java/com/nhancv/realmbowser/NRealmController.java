package com.nhancv.realmbowser;

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

        if (uri.startsWith("/api")) {
            String schema = realmDiscovery.getSchemas();
            return newFixedLengthResponse(NanoHTTPD.Response.Status.ACCEPTED, "application/json", schema);
        } else {
            return newFixedLengthResponse(uri + "<br>" + "Coming soon ...");
        }
    }

}
