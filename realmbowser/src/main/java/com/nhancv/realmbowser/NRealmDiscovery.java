package com.nhancv.realmbowser;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmDiscovery {

    public NRealmDiscovery(Context context, RealmConfiguration realmConfiguration) {
        Realm.init(context);
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
