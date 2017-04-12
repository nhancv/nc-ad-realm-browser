package com.nhancv.realmbowser;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by nhancao on 4/12/17.
 */

public interface RealmController {

    NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession session);

}
