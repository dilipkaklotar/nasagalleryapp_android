package com.obvious.nasagallery.ui.description;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.obvious.nasagallery.R;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.databinding.FragmentDescriptionBinding;
import com.obvious.nasagallery.ui.MainActivity;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.NasaGalleryConstants;

public class DescriptionFragment extends BaseFragment implements NasaGalleryConstants {

    Context mContext;
    FragmentDescriptionBinding binding;

    NasaData nasaData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public DescriptionFragment() {

    }

    public static DescriptionFragment newInstance() {
        return new DescriptionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            nasaData = (NasaData) requireArguments().getSerializable(KEY_OBJ_NASA_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, container, false);
        binding.setModel(nasaData);

        binding.btnBack.setOnClickListener(v -> ((MainActivity) binding.getRoot().getContext()).onBackPressed());

        return binding.getRoot();

    }

}
