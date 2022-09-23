package com.obvious.nasagallery.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.obvious.nasagallery.R;
import com.obvious.nasagallery.databinding.FragmentDashboardBinding;
import com.obvious.nasagallery.ui.MainActivity;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.NasaGalleryConstants;

public class DashboardFragment extends BaseFragment implements NasaGalleryConstants {

    Context mContext;
    FragmentDashboardBinding binding;
    DashboardFragmentViewModel dashboardFragmentViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public DashboardFragment() {

    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        dashboardFragmentViewModel = new DashboardFragmentViewModel(((MainActivity) mContext).getApplication(), binding, currentTab, fragmentInteractionCallback);


        return binding.getRoot();

    }


}
