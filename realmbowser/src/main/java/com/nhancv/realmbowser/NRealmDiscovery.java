package com.nhancv.realmbowser;

import android.content.Context;

import com.google.gson.Gson;
import com.nhancv.realmbowser.model.Schema;
import com.nhancv.realmbowser.model.SchemaResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by nhancao on 4/12/17.
 */

public class NRealmDiscovery {

    public NRealmDiscovery(Context context, RealmConfiguration realmConfiguration) {
        Realm.init(context);
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public String getSchemas() {
        Realm realm = Realm.getDefaultInstance();

        DynamicRealm dynamicRealm = DynamicRealm.getInstance(realm.getConfiguration());
        Set<Class<? extends RealmModel>> modelClasses = realm.getConfiguration().getRealmObjectClasses();

        List<Schema> schemaList = new ArrayList<>();
        for (Class<? extends RealmModel> modelClass : modelClasses) {
            RealmSchema rSchema = dynamicRealm.getSchema();
            RealmObjectSchema realmObjectSchema = rSchema.get(modelClass.getSimpleName());
            Set<String> fieldNames = realmObjectSchema.getFieldNames();
            List<Schema.Structure> structures = new ArrayList<>();
            for (String fieldName : fieldNames) {
                RealmFieldType realmFieldType = realmObjectSchema.getFieldType(fieldName);
                Schema.Structure structure = new Schema.Structure(fieldName, realmFieldType.name());
                structures.add(structure);
            }
            schemaList.add(new Schema(modelClass.getSimpleName(), structures));
        }


        realm.close();

        Gson gson = new Gson();
        return gson.toJson(new SchemaResponse(schemaList));

    }


}
