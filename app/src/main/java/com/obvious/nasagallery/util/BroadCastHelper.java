package com.obvious.nasagallery.util;


import android.content.Context;
import android.content.Intent;

public class BroadCastHelper {

    public static void broadCastNetworkChanged(Context context, boolean isConnected) {
        Intent intent = new Intent();
        intent.setAction(NasaGalleryConstants.RECEIVER_NETWORK_CHANGED);
        intent.putExtra(NasaGalleryConstants.NETWORK_AVAILABLE, isConnected);
        Utility.sendBroadCast(context, intent);
    }
}
