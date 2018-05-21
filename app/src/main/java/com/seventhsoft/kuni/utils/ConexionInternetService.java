package com.seventhsoft.kuni.utils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.seventhsoft.kuni.player.DialogoFragment;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by olimpia on 15/12/16.
 */

public class ConexionInternetService extends Service {
    private Subscription internetConnectivitySubscription;
    boolean internetEnable;
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            startInternetStatus();
        }
    }
    public class LocalBinder extends Binder {
        ConexionInternetService getService() {
            return ConexionInternetService.this;
        }
    }
    @Override
    public void onCreate() {
        //Toast.makeText(this, "service create", Toast.LENGTH_SHORT).show();
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
        //mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        // Display a notification about us starting.  We put an icon in the status bar.
        //showNotification();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startInternetStatus();

        //Toast.makeText(this, "LocalService Received start id " + startId + ": " + intent, Toast.LENGTH_LONG).show();

        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        return START_REDELIVER_INTENT;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private final IBinder mBinder = new LocalBinder();

    private void startInternetStatus() {
        final ConexionInternetPreference conexionInternetPreference = ConexionInternetPreference
                .getInstance(getApplicationContext());
        internetConnectivitySubscription = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isConnectedToInternet) {
                        Log.i("Internet", "OSE| Internet status: " + isConnectedToInternet);
                        internetEnable = isConnectedToInternet;
                        conexionInternetPreference.saveData("estatusInternet", internetEnable);

                    }
                });
    }
}



