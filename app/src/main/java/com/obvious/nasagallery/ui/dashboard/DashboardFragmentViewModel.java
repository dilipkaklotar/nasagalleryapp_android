package com.obvious.nasagallery.ui.dashboard;

import android.app.Application;
import android.app.Dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.obvious.nasagallery.R;
import com.obvious.nasagallery.adapter.DashboardAdapter;
import com.obvious.nasagallery.common.clickhandler.MethodCallListener;
import com.obvious.nasagallery.common.viewmodel.AppViewModel;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.databinding.FragmentDashboardBinding;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.Constants;
import com.obvious.nasagallery.util.CustomProgressDialog;
import com.obvious.nasagallery.util.DataBuilder;
import com.obvious.nasagallery.util.DialogManager;
import com.obvious.nasagallery.util.NasaGalleryConstants;
import com.obvious.nasagallery.util.PreferenceManager;
import com.obvious.nasagallery.util.Utility;
import com.obvious.nasagallery.util.view.kprogresshud.KProgressHUD;

import java.lang.reflect.Type;
import java.util.List;

public class DashboardFragmentViewModel extends AppViewModel implements NasaGalleryConstants {

    FragmentDashboardBinding binder;

    String mCurrentTab;
    BaseFragment.FragmentInteractionCallback mCallback;

    PreferenceManager mPreferenceManager;

    StaggeredGridLayoutManager manager;
    DashboardAdapter dashboardAdapter;

    MethodCallListener methodCallListener;

    public DashboardFragmentViewModel(Application application, FragmentDashboardBinding binder, String currentTab, BaseFragment.FragmentInteractionCallback interactionCallback) {
        super(application, ((AppCompatActivity) Utility.getViewActivity(binder)).getSupportFragmentManager());
        this.binder = binder;
        this.mCurrentTab = currentTab;
        this.mCallback = interactionCallback;
        this.mPreferenceManager = new PreferenceManager(getContext());
        initiate();
    }

    private void initiate() {

        methodCallListener = new MethodCallListener() {
            @Override
            public void callMethod() {
                doGetAllFeed(true, mCurrentTab, mCallback);
            }
        };

        if (isNetConnected()) {
            doGetAllFeed(true, mCurrentTab, mCallback);
        } else {
            DialogManager.showDialogWithListener(binder.getRoot().getContext(),
                    binder.getRoot().getContext().getString(R.string.alert),
                    binder.getRoot().getContext().getString(R.string.alert_message),
                    methodCallListener);
        }

        binder.executePendingBindings();
    }

    private void doGetAllFeed(boolean isVisible, String mCurrentTab, BaseFragment.FragmentInteractionCallback mCallback) {

        KProgressHUD kProgressHUD = new CustomProgressDialog(binder.getRoot().getContext()).getkProgressHUD();

        if (isVisible)
            kProgressHUD.show();

        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binder.recyclerView.setLayoutManager(manager);

        dashboardAdapter = new DashboardAdapter(binder.getRoot().getContext(), DataBuilder.getNasaDataList(binder.getRoot().getContext()), mCurrentTab, mCallback);
        binder.recyclerView.setAdapter(dashboardAdapter);

        if (isVisible)
            if (kProgressHUD.isShowing())
                kProgressHUD.dismiss();

    }

}
