package com.obvious.nasagallery.util;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class NetworkSchedulerService extends JobService implements
        ConnectionReceiver.ConnectivityReceiverListener {
    public static String RECEIVER_NETWORK_CHANGED = "RECEIVER_NETWORK_CHANGED";
    public static String NETWORK_AVAILABLE = "NETWORK_AVAILABLE";
    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private ConnectionReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        mConnectivityReceiver = new ConnectionReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }

    /**
     * When the app's MainActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob" + mConnectivityReceiver);
        registerReceiver(mConnectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        try{
            if(mConnectivityReceiver != null){
                unregisterReceiver(mConnectivityReceiver);
                mConnectivityReceiver = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        BroadCastHelper.broadCastNetworkChanged(getApplicationContext(), isConnected);
    }
}
