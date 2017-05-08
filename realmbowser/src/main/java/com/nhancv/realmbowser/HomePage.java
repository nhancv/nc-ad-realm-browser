package com.nhancv.realmbowser;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by nhancao on 4/14/17.
 */

public class HomePage {

    public static String content = "Build content error";

    public static void buildContent(Context context) {
        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = context.getAssets().open("index.html");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();

            content = buf.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
