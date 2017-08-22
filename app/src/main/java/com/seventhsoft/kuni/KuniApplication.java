package com.seventhsoft.kuni;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.seventhsoft.kuni.models.modelsrealm.KuniFieldsModule;
import com.seventhsoft.kuni.models.modelsrealm.MyMigration;
import com.seventhsoft.kuni.utils.ConexionInternetService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by olibits on 21/07/17.
 */

public class KuniApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        /**
         * Realm configuracion
         */
        Intent intent = new Intent(this, ConexionInternetService.class);
        startService(intent);


        /**
         * Realm configuracion
         */


        Realm.init(this);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("Kuni.realm")
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new MyMigration()) // Migration to run instead of throwing an exception
                .modules(new KuniFieldsModule())
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Context getContext() {
        return context;
    }

}
