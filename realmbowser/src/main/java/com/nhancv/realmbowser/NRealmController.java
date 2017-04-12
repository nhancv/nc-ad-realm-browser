package com.nhancv.realmbowser;

import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmController implements RealmController {

    private NRealmDiscovery realmDiscovery;

    public NRealmController(NRealmDiscovery realmDiscovery) {
        this.realmDiscovery = realmDiscovery;
    }

    @Override
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session) {
        return newFixedLengthResponse(NanoHTTPD.Response.Status.ACCEPTED, "application/json", "{\"data\": null}");
    }

}
