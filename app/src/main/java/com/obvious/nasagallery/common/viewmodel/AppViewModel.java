package com.obvious.nasagallery.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.obvious.nasagallery.databinding.CustomLoaderBinding;

public class AppViewModel extends APIViewModel {

    private static final String TAG = AppViewModel.class.getName();

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public AppViewModel(@NonNull Application application, FragmentManager fragmentManager) {
        super(application, fragmentManager);
    }

    public AppViewModel getAppViewModel() {
        return this;
    }

    public void showLoader(CustomLoaderBinding loaderBinding) {
        loaderBinding.setIsLoading(true);
    }

    public void hideLoader(CustomLoaderBinding loaderBinding) {
        loaderBinding.setIsLoading(false);
    }
}
