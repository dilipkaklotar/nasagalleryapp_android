package com.obvious.nasagallery.util;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {

    public static void toast(Context context, String toastMessage) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(toastMessage);
        toast.show();
    }

}
