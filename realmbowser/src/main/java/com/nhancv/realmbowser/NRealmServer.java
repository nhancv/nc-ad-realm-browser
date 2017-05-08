package com.nhancv.realmbowser;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.util.Locale;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmServer {
    private static final String TAG = NRealmServer.class.getSimpleName();

    private int port = 8765;
    private NRealmNanoHTTPD realmNanoHTTPD;
    private RealmController realmController;
    private boolean enableCorns;

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

        //Build content
        HomePage.buildContent(realmDiscovery.getContext());



    }

    public static boolean start() {
        try {
            getInstance().getRealmNanoHTTPD().start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isStart() {
        return getInstance() != null &&
               getInstance().getRealmNanoHTTPD() != null &&
               getInstance().getRealmNanoHTTPD().isAlive();
    }

    public static boolean stop() {
        try {
            getInstance().getRealmNanoHTTPD().stop();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getServerAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formatedIpAddress = String
                .format(Locale.US, "%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
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

    public boolean isEnableCorns() {
        return enableCorns;
    }

    public void setEnableCorns(boolean enableCorns) {
        this.enableCorns = enableCorns;
    }

    private static class NRealmNanoHTTPD extends NanoHTTPD {

        NRealmNanoHTTPD(String hostname, int port) {
            super(hostname, port);
        }

        @Override
        public Response serve(IHTTPSession session) {

            String uri = session.getUri();
            String apiList = "/api<br>" +
                             "/api?where={table_name}<br>" +
                             "/api?where={table_name}&all<br>" +
                             "/api?where={table_name}&field={column_name}&equal={value}<br>" +
                             "/api?where={table_name}&field={column_name}&begin={value}<br>" +
                             "/api?where={table_name}&field={column_name}&contains={value}<br>";

            String homePage = HomePage.content.replace("-----[HOSTNAME:PORT]------", "");

            Response response;
            if (uri.startsWith("/api/list")) {
                response = newFixedLengthResponse("Api list: <br>" + apiList + "<br>");
            } else if (uri.startsWith("/api")) {
                response = getInstance().getRealmController().serve(session);
            } else {
                response = newFixedLengthResponse(homePage);
            }

            if (getInstance().isEnableCorns()) {
                response.addHeader("Access-Control-Allow-Methods", "DELETE, GET, POST, PUT");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin,accept,content-type,Authorization");
            }
            return response;
        }

    }

    private static class SingletonHelper {
        private static final NRealmServer INSTANCE = new NRealmServer();
    }
}
