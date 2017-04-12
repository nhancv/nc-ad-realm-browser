package com.nhancv.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.nhancv.realmbowser.NRealmDiscovery;
import com.nhancv.realmbowser.NRealmServer;
import com.nhancv.sample.model.Person;
import com.nhancv.sample.model.User;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private NRealmServer realmServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.activity_main_tv_msg);

        initRealm();
        String address = realmServer.getServerAddress(this);
        textView.setText(address);
        Log.e(TAG, "Server address: " + address);

        genSampleRealm();
        viewSample();
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            realmServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        realmServer.stop();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(config);

        realmServer = new NRealmServer(new NRealmDiscovery(this, config));

    }

    private void genSampleRealm() {
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setAge(i);
            user.setName("User-" + i);

            Person person = new Person();
            person.setName("Person-" + i);
            realm.copyToRealm(user);
            realm.copyToRealm(person);
        }
        realm.commitTransaction();

    }

    private void viewSample() {
        RealmSchema schema = Realm.getDefaultInstance().getSchema();
        for (RealmObjectSchema realmObjectSchema : schema.getAll()) {
            Log.e(TAG, "RealmSchema: " + realmObjectSchema.getClassName());
            for (String s : realmObjectSchema.getFieldNames()) {
                Log.e(TAG, "onCreate:filed name: " + s);
            }
        }
        RealmResults<User> users = Realm.getDefaultInstance().where(User.class).findAll();
        for (User user : users) {
            Log.e(TAG, "onCreate: " + user);
        }
    }
}
