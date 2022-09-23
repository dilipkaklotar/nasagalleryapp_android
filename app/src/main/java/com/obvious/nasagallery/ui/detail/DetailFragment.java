package com.obvious.nasagallery.ui.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.obvious.nasagallery.R;
import com.obvious.nasagallery.databinding.FragmentDetailBinding;
import com.obvious.nasagallery.ui.MainActivity;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.NasaGalleryConstants;

public class DetailFragment extends BaseFragment implements NasaGalleryConstants {

    Context mContext;
    FragmentDetailBinding binding;
    DetailFragmentViewModel detailFragmentViewModel;

    int currentIndex = 0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public DetailFragment() {

    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            currentIndex = requireArguments().getInt(KEY_CURRENT_INDEX);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        detailFragmentViewModel = new DetailFragmentViewModel(((MainActivity) mContext).getApplication(), binding, currentTab, fragmentInteractionCallback, currentIndex);

        binding.btnBack.setOnClickListener(v -> ((MainActivity) binding.getRoot().getContext()).onBackPressed());

        if(isNetConnected()){
            binding.btnBack.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.btnDetail.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.btnZoom.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white), android.graphics.PorterDuff.Mode.MULTIPLY);
        } else {
            binding.btnBack.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.btnDetail.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
            binding.btnZoom.setColorFilter(ContextCompat.getColor(binding.getRoot().getContext(), R.color.black), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        return binding.getRoot();

    }

}
