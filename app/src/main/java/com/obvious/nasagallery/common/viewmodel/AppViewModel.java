package com.obvious.nasagallery.common.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.obvious.nasagallery.databinding.CustomLoaderBinding;

public class AppViewModel extends APIViewModel {

    String TAG = AppViewModel.class.getName();

    public AppViewModel(@NonNull Application application) {
        super(application);
    }

    public AppViewModel(@NonNull Application application, FragmentManager fragmentManager) {
        super(application, fragmentManager);
    }

    public AppViewModel getAppViewModel() {
        return this;
    }

    // Show Loader in middle of the screen
    public void showLoader(CustomLoaderBinding loaderBinding) {
        loaderBinding.setIsLoading(true);
    }

    // Hide Loader in middle of the screen
    public void hideLoader(CustomLoaderBinding loaderBinding) {
        loaderBinding.setIsLoading(false);
    }
}
