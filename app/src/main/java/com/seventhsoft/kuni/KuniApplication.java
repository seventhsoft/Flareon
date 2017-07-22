package com.seventhsoft.kuni;

import android.app.Application;

import com.seventhsoft.kuni.model.modelsrealm.KuniFieldsModule;
import com.seventhsoft.kuni.model.modelsrealm.MyMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by olibits on 21/07/17.
 */

public class KuniApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("CodigaLocal.realm")
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new MyMigration()) // Migration to run instead of throwing an exception
                .modules(new KuniFieldsModule())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
