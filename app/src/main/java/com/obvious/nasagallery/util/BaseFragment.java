package com.obvious.nasagallery.util;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.obvious.nasagallery.NasaGalleryApp;
import com.obvious.nasagallery.R;


public class BaseFragment extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {

    protected FragmentInteractionCallback fragmentInteractionCallback;
    protected static String currentTab;
    private boolean isNetAvailable = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Simple Name", this.getClass().getSimpleName());
        Log.d("Name", this.getClass().getName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInteractionCallback = (FragmentInteractionCallback) context;
            setNetworkChangeListener();
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString() + " must implement " + FragmentInteractionCallback.class.getName());
        }
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
        ToastManager.toast(getContext(), getContext().getString(R.string.toast_error_no_internet));
        return false;
    }

    protected boolean isNetConnected(boolean isShowToast) {
        if (isShowToast) {
            return isNetConnected();
        }
        return false;
    }


    @Override
    public void onDetach() {
        fragmentInteractionCallback = null;
        super.onDetach();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    public interface FragmentInteractionCallback {
        void onFragmentInteractionCallback(Bundle bundle);
    }

    public static void setCurrentTab(String currentTab) {
        BaseFragment.currentTab = currentTab;
    }
}
