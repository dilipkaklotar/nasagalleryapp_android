package com.obvious.nasagallery.ui.detail;

import android.app.Application;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.obvious.nasagallery.adapter.DetailAdapter;
import com.obvious.nasagallery.common.viewmodel.AppViewModel;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.databinding.FragmentDetailBinding;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.DataBuilder;
import com.obvious.nasagallery.util.FragmentUtils;
import com.obvious.nasagallery.util.NasaGalleryConstants;
import com.obvious.nasagallery.util.PreferenceManager;
import com.obvious.nasagallery.util.Utility;

public class DetailFragmentViewModel extends AppViewModel implements NasaGalleryConstants {

    FragmentDetailBinding binder;

    String mCurrentTab;
    BaseFragment.FragmentInteractionCallback mCallback;

    PreferenceManager mPreferenceManager;

    LinearLayoutManager layoutManager;

    DetailAdapter detailAdapter;

    int currentIndex  = 0;

    public DetailFragmentViewModel(Application application, FragmentDetailBinding binder, String currentTab,
                                   BaseFragment.FragmentInteractionCallback interactionCallback, int currentIndex) {
        super(application, ((AppCompatActivity) Utility.getViewActivity(binder)).getSupportFragmentManager());
        this.binder = binder;
        this.mCurrentTab = currentTab;
        this.mCallback = interactionCallback;
        this.currentIndex = currentIndex;
        this.mPreferenceManager = new PreferenceManager(getContext());
        initiate();
    }

    private void initiate() {

        layoutManager = new LinearLayoutManager(binder.getRoot().getContext(), LinearLayoutManager.HORIZONTAL, false);
        binder.recycleView.setLayoutManager(layoutManager);

        detailAdapter = new DetailAdapter(binder.getRoot().getContext(), mCurrentTab, mCallback);
        detailAdapter.setData(DataBuilder.getNasaDataList(binder.getRoot().getContext()));

        binder.recycleView.setAdapter(detailAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        binder.recycleView.setHasFixedSize(true);
        binder.recycleView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(binder.recycleView);

        binder.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    currentIndex = layoutManager.getPosition(centerView);
                }
            }
        });

        binder.recycleView.scrollToPosition(currentIndex);

        binder.btnZoom.setOnClickListener(v -> {

            NasaData nasaData = DataBuilder.getNasaDataList(binder.getRoot().getContext()).get(currentIndex);
            FragmentUtils.sendActionToActivity(ACTION_ZOOM,
                    mCurrentTab, nasaData,true, mCallback);

        });

        binder.btnDetail.setOnClickListener(view -> {
            NasaData nasaData = DataBuilder.getNasaDataList(binder.getRoot().getContext()).get(currentIndex);
            FragmentUtils.sendActionToActivity(ACTION_DESCRIPTION,
                    mCurrentTab, nasaData,true, mCallback);
        });

        binder.executePendingBindings();
    }
}
