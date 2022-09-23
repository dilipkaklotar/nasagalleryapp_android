package com.obvious.nasagallery.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class APIViewModel extends BaseViewModel{

    private static final String TAG = "APIViewModel";

    public APIViewModel(@NonNull Application application) {
        super(application);
    }

    public APIViewModel(@NonNull Application application, FragmentManager fragmentManager) {
        super(application,fragmentManager);
    }

}
