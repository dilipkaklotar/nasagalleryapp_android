package com.obvious.nasagallery.util;


import android.content.Context;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;

import com.obvious.nasagallery.R;
import com.obvious.nasagallery.util.view.kprogresshud.KProgressHUD;


public class CustomProgressDialog {

    Context mContext;
    KProgressHUD kProgressHUD;

    public CustomProgressDialog(Context context) {
        this.mContext = context;



    }

    public KProgressHUD getkProgressHUD(){

        ProgressBar pgBar = new ProgressBar(mContext);
        pgBar.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_loader));
        pgBar.setPadding(20, 20, 20, 20);
        pgBar.setIndeterminate(true);

        kProgressHUD = KProgressHUD.create(mContext)
                .setCustomView(pgBar)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        return kProgressHUD;
    }

}

