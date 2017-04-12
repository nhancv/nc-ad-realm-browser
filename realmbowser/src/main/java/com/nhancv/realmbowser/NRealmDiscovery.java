package com.nhancv.realmbowser;

import android.content.Context;

import com.google.gson.Gson;
import com.nhancv.realmbowser.model.DataResponse;
import com.nhancv.realmbowser.model.Schema;
import com.nhancv.realmbowser.model.SchemaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmQuery;
import io.realm.RealmResults;
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

    public String query(String where, String field, NRealmController.QUERY action, String value) {

        try {
            Realm realm = Realm.getDefaultInstance();
            DynamicRealm dynamicRealm = DynamicRealm.getInstance(realm.getConfiguration());
            Set<Class<? extends RealmModel>> modelClasses = realm.getConfiguration().getRealmObjectClasses();
            Class<? extends RealmModel> modelModel = null;
            for (Class<? extends RealmModel> modelClass : modelClasses) {
                if (modelClass.getSimpleName().equals(where)) {
                    modelModel = modelClass;
                    break;
                }
            }

            if (modelModel != null) {
                RealmSchema schema = dynamicRealm.getSchema();
                RealmObjectSchema realmObjectSchema = schema.get(modelModel.getSimpleName());

                List<String> columns = new ArrayList<>(realmObjectSchema.getFieldNames());

                RealmFieldType realmFieldType = realmObjectSchema.getFieldType(field);
                RealmFieldType[] realmFieldTypes = new RealmFieldType[columns.size()];
                int index = 0;
                for (String fieldName : columns) {
                    realmFieldTypes[index] = realmObjectSchema.getFieldType(fieldName);
                    index++;
                }

                RealmQuery<DynamicRealmObject> realmQuery = dynamicRealm.where(modelModel.getSimpleName());
                switch (realmFieldType) {
                    case INTEGER:
                        switch (action) {
                            case EQUAL:
                                realmQuery.equalTo(field, Long.parseLong(value));
                                break;
                            case BEGIN:
                                realmQuery.beginsWith(field, value);
                                break;
                            default:
                                realmQuery.contains(field, value);
                                break;
                        }
                        break;
                    case BOOLEAN:
                        switch (action) {
                            case EQUAL:
                                realmQuery.equalTo(field, Boolean.parseBoolean(value));
                                break;
                            case BEGIN:
                                realmQuery.beginsWith(field, value);
                                break;
                            default:
                                realmQuery.contains(field, value);
                                break;
                        }
                        break;
                    case FLOAT:
                        switch (action) {
                            case EQUAL:
                                realmQuery.equalTo(field, Float.parseFloat(value));
                                break;
                            case BEGIN:
                                realmQuery.beginsWith(field, value);
                                break;
                            default:
                                realmQuery.contains(field, value);
                                break;
                        }
                        break;
                    case DOUBLE:
                        switch (action) {
                            case EQUAL:
                                realmQuery.equalTo(field, Double.parseDouble(value));
                                break;
                            case BEGIN:
                                realmQuery.beginsWith(field, value);
                                break;
                            default:
                                realmQuery.contains(field, value);
                                break;
                        }
                        break;
                    case STRING:
                        switch (action) {
                            case EQUAL:
                                realmQuery.equalTo(field, value);
                                break;
                            case BEGIN:
                                realmQuery.beginsWith(field, value);
                                break;
                            default:
                                realmQuery.contains(field, value);
                                break;
                        }
                        break;
                }
                RealmResults<DynamicRealmObject> realmResults = realmQuery.findAll();
                int totalSize = realmResults.size();

                List<List<Object>> rows = new ArrayList<>();
                for (int i = 0; i < totalSize; i++) {
                    DynamicRealmObject dynamicRealmObject = realmResults.get(i);
                    List<Object> rowData = new ArrayList<>();
                    for (int j = 0; j < columns.size(); j++) {
                        String columnName = columns.get(j);
                        Object res = null;
                        switch (realmFieldTypes[j]) {
                            case INTEGER:
                                res = dynamicRealmObject.getLong(columnName);
                                break;
                            case BOOLEAN:
                                res = dynamicRealmObject.getBoolean(columnName);
                                break;
                            case FLOAT:
                                res = dynamicRealmObject.getFloat(columnName);
                                break;
                            case DOUBLE:
                                res = dynamicRealmObject.getDouble(columnName);
                                break;
                            case DATE:
                                res = dynamicRealmObject.getDate(columnName).toString();
                                break;
                            case STRING:
                                res = dynamicRealmObject.getString(columnName);
                                break;
                        }
                        rowData.add(res);
                    }
                    rows.add(rowData);
                }
                realm.close();

                for (int i = 0; i < columns.size(); i++) {
                    String column = columns.get(i);
                    column = column + " (" + realmFieldTypes[i] + ")";
                    columns.set(i, column);
                }

                SchemaData schemaData = new SchemaData(columns, rows, totalSize);
                return toJson(schemaData);

            } else {
                throw new ClassNotFoundException("ClassNotFoundException");
            }
        } catch (ClassNotFoundException e) {
            return toJson(e);
        }
    }

    public String query(String where) {

        try {
            Realm realm = Realm.getDefaultInstance();
            DynamicRealm dynamicRealm = DynamicRealm.getInstance(realm.getConfiguration());
            Set<Class<? extends RealmModel>> modelClasses = realm.getConfiguration().getRealmObjectClasses();
            Class<? extends RealmModel> modelModel = null;
            for (Class<? extends RealmModel> modelClass : modelClasses) {
                if (modelClass.getSimpleName().equals(where)) {
                    modelModel = modelClass;
                    break;
                }
            }

            if (modelModel != null) {
                RealmSchema schema = dynamicRealm.getSchema();
                RealmObjectSchema realmObjectSchema = schema.get(modelModel.getSimpleName());

                List<String> columns = new ArrayList<>(realmObjectSchema.getFieldNames());

                RealmFieldType[] realmFieldTypes = new RealmFieldType[columns.size()];
                int index = 0;
                for (String fieldName : columns) {
                    realmFieldTypes[index] = realmObjectSchema.getFieldType(fieldName);
                    index++;
                }
                RealmQuery<DynamicRealmObject> realmQuery = dynamicRealm.where(modelModel.getSimpleName());
                RealmResults<DynamicRealmObject> realmResults = realmQuery.findAll();
                int totalSize = realmResults.size();

                List<List<Object>> rows = new ArrayList<>();
                for (int i = 0; i < totalSize; i++) {
                    DynamicRealmObject dynamicRealmObject = realmResults.get(i);
                    List<Object> rowData = new ArrayList<>();
                    for (int j = 0; j < columns.size(); j++) {
                        String columnName = columns.get(j);
                        Object res = null;
                        switch (realmFieldTypes[j]) {
                            case INTEGER:
                                res = dynamicRealmObject.getLong(columnName);
                                break;
                            case BOOLEAN:
                                res = dynamicRealmObject.getBoolean(columnName);
                                break;
                            case FLOAT:
                                res = dynamicRealmObject.getFloat(columnName);
                                break;
                            case DOUBLE:
                                res = dynamicRealmObject.getDouble(columnName);
                                break;
                            case DATE:
                                res = dynamicRealmObject.getDate(columnName).toString();
                                break;
                            case STRING:
                                res = dynamicRealmObject.getString(columnName);
                                break;
                        }
                        rowData.add(res);
                    }
                    rows.add(rowData);
                }
                realm.close();

                for (int i = 0; i < columns.size(); i++) {
                    String column = columns.get(i);
                    column = column + " (" + realmFieldTypes[i] + ")";
                    columns.set(i, column);
                }

                SchemaData schemaData = new SchemaData(columns, rows, totalSize);
                return toJson(schemaData);

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
