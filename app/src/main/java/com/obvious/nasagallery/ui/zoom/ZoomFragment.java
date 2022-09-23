package com.obvious.nasagallery.ui.zoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.obvious.nasagallery.NasaGalleryApp;
import com.obvious.nasagallery.R;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.databinding.FragmentZoomBinding;
import com.obvious.nasagallery.ui.MainActivity;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.ConnectivityReceiver;
import com.obvious.nasagallery.util.NasaGalleryConstants;

public class ZoomFragment extends BaseFragment implements NasaGalleryConstants {

    Context mContext;
    FragmentZoomBinding binding;

    NasaData nasaData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public ZoomFragment() {

    }

    public static ZoomFragment newInstance() {
        return new ZoomFragment();
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_zoom, container, false);

        binding.btnBack.setOnClickListener(v -> ((MainActivity) binding.getRoot().getContext()).onBackPressed());

        if (ConnectivityReceiver.isConnected()) {

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.download_placeholder)
                    .showImageOnFail(R.drawable.image_placeholder)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();

            NasaGalleryApp.getImageLoader()
                    .displayImage(nasaData.getHdurl(),
                            new ImageViewAware( binding.galleryImage), options);

        } else {
            binding.galleryImage.setImageResource(R.drawable.image_placeholder);
        }


        return binding.getRoot();

    }
}
