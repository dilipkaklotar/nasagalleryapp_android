package com.obvious.nasagallery.common.viewmodel;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;

import com.obvious.nasagallery.NasaGalleryApp;
import com.obvious.nasagallery.util.ConnectivityReceiver;


public class BaseViewModel extends AndroidViewModel {
    private boolean isNetAvailable = false;
    protected FragmentManager fragmentManager;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        setNetworkChangeListener();
    }

    public BaseViewModel(@NonNull Application application, FragmentManager fragmentManager) {
        super(application);
        this.fragmentManager = fragmentManager;
        setNetworkChangeListener();
    }

    private void setNetworkChangeListener() {
        isNetAvailable = ConnectivityReceiver.isConnected();
        NasaGalleryApp.getInstance().setConnectivityListener(isConnected -> {
            isNetAvailable = isConnected;

        });
    }

    protected boolean isNetConnected() {
        if (isNetAvailable) {
            return true;
        }
        return false;
    }

    protected boolean isNetConnected(boolean isShowToast) {
        if (isShowToast) {
            return isNetConnected();
        }
        return false;
    }

    protected Context getContext() {
        return getApplication().getApplicationContext();
    }

}
