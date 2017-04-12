package com.nhancv.realmbowser;

import android.content.Context;

import com.google.gson.Gson;
import com.nhancv.realmbowser.model.DataResponse;
import com.nhancv.realmbowser.model.Schema;

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
    private static final String TAG = NRealmDiscovery.class.getSimpleName();

    public NRealmDiscovery(Context context, RealmConfiguration realmConfiguration) {
        Realm.init(context);
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public String getSchema() {
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

        return toJson(schemaList);
    }

    public String getSchema(String tableName) {

        try {
            Realm realm = Realm.getDefaultInstance();
            DynamicRealm dynamicRealm = DynamicRealm.getInstance(realm.getConfiguration());
            Set<Class<? extends RealmModel>> modelClasses = realm.getConfiguration().getRealmObjectClasses();
            Class<? extends RealmModel> modelModel = null;
            for (Class<? extends RealmModel> modelClass : modelClasses) {
                if (modelClass.getSimpleName().equals(tableName)) {
                    modelModel = modelClass;
                    break;
                }
            }

            if (modelModel != null) {
                RealmSchema rSchema = dynamicRealm.getSchema();
                RealmObjectSchema realmObjectSchema = rSchema.get(modelModel.getSimpleName());
                Set<String> fieldNames = realmObjectSchema.getFieldNames();
                List<Schema.Structure> structures = new ArrayList<>();
                for (String fieldName : fieldNames) {
                    RealmFieldType realmFieldType = realmObjectSchema.getFieldType(fieldName);
                    Schema.Structure structure = new Schema.Structure(fieldName, realmFieldType.name());
                    structures.add(structure);
                }
                realm.close();
                return toJson(new Schema(tableName, structures));
            } else {
                throw new ClassNotFoundException("ClassNotFoundException");
            }
        } catch (ClassNotFoundException e) {
            return toJson(e);
        }
    }

    public String defaultReturn() {
        return toJson(null);
    }

    public <T> String toJson(T data) {
        return new Gson().toJson(new DataResponse<>(data));
    }


}
