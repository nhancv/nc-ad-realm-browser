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

    public NRealmServer() {
        this(8765);
    }

    public NRealmServer(int port) {
        this(null, port);
    }

    public NRealmServer(String hostname, int port) {
        super(hostname, port);
        this.port = port;
    }

    @Override
    public Response serve(IHTTPSession session) {
        return newFixedLengthResponse("Hello word!");
    }

    public String getServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format(Locale.US, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formatedIpAddress + ":" + port;
    }
}
