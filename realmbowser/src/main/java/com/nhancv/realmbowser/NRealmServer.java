package com.nhancv.realmbowser;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.util.Locale;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmServer {

    private int port = 8765;
    private NRealmNanoHTTPD realmNanoHTTPD;
    private RealmController realmController;

    public static NRealmServer getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public static void init(NRealmDiscovery realmDiscovery) {
        init(getInstance().getPort(), realmDiscovery);
    }

    public static void init(int port, NRealmDiscovery realmDiscovery) {
        init(null, port, realmDiscovery);
    }

    public static void init(String hostName, int port, NRealmDiscovery realmDiscovery) {
        getInstance().setPort(port);
        getInstance().setRealmController(new NRealmController(realmDiscovery));
        getInstance().setRealmNanoHTTPD(new NRealmNanoHTTPD(hostName, port));
    }

    public static void start() {
        try {
            getInstance().getRealmNanoHTTPD().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        getInstance().getRealmNanoHTTPD().stop();
    }

    public static String getServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String.format(Locale.US, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formatedIpAddress + ":" + getInstance().getPort();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NRealmNanoHTTPD getRealmNanoHTTPD() {
        return realmNanoHTTPD;
    }

    public void setRealmNanoHTTPD(NRealmNanoHTTPD realmNanoHTTPD) {
        this.realmNanoHTTPD = realmNanoHTTPD;
    }

    public RealmController getRealmController() {
        return realmController;
    }

    public void setRealmController(RealmController realmController) {
        this.realmController = realmController;
    }

    private static class NRealmNanoHTTPD extends NanoHTTPD {

        NRealmNanoHTTPD(String hostname, int port) {
            super(hostname, port);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String uri = session.getUri();

            if (uri.startsWith("/api")) {
                return getInstance().getRealmController().serve(session);
            } else {

                String api = "/api<br>" +
                        "/api?where={table_name}<br>" +
                        "/api?where={table_name}&all<br>" +
                        "/api?where={table_name}&field={column_name}&equal={value}<br>" +
                        "/api?where={table_name}&field={column_name}&begin={value}<br>" +
                        "/api?where={table_name}&field={column_name}&contains={value}<br>";

                return newFixedLengthResponse("Api list: <br>" + api + "<br>");
            }
        }

    }

    private static class SingletonHelper {
        private static final NRealmServer INSTANCE = new NRealmServer();
    }
}
