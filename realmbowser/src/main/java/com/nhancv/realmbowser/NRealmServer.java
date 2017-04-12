package com.nhancv.realmbowser;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.util.Locale;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmServer extends NanoHTTPD {

    private int port = 8765;
    private RealmController realmController;

    public NRealmServer(NRealmDiscovery realmDiscovery) {
        this(8765, realmDiscovery);
    }

    public NRealmServer(int port, NRealmDiscovery realmDiscovery) {
        this(null, port, realmDiscovery);
    }

    public NRealmServer(String hostname, int port, NRealmDiscovery realmDiscovery) {
        super(hostname, port);
        this.port = port;
        this.realmController = new NRealmController(realmDiscovery);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();

        if (uri.startsWith("/api")) {
            return realmController.serve(session);
        } else {
            return newFixedLengthResponse(uri + "<br>" + "Coming soon ...");
        }
    }

    public String getServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format(Locale.US, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formatedIpAddress + ":" + port;
    }
}
