package com.obvious.nasagallery.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.obvious.nasagallery.NasaGalleryApp;
import com.obvious.nasagallery.R;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.ConnectivityReceiver;
import com.obvious.nasagallery.util.FragmentUtils;
import com.obvious.nasagallery.util.NasaGalleryConstants;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder>
        implements NasaGalleryConstants {

    Context mContext;

    String mCurrentTab;
    BaseFragment.FragmentInteractionCallback mCallback;

    List<NasaData> nasaDataList;

    public DashboardAdapter(Context context,
                            List<NasaData> nasaDataList,
                            String currentTab,
                            BaseFragment.FragmentInteractionCallback interactionCallback) {
        this.mContext = context;
        this.nasaDataList = nasaDataList;
        this.mCurrentTab = currentTab;
        this.mCallback = interactionCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_dashboard_item, viewGroup, false);
//        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
//        params.height = 350;
//        view.setLayoutParams(params);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {
        holder.setIsRecyclable(false);

        NasaData data = nasaDataList.get(holder.getAdapterPosition());

        // Check Internet connection if net available then load images from internet
        // else load placeholder image
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
                    .displayImage(data.getUrl(),
                            new ImageViewAware(holder.imageView), options);

        } else {
            holder.imageView.setImageResource(R.drawable.image_placeholder);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirect to detail page with index parameter
                FragmentUtils.sendActionToDetail(ACTION_DETAIL,
                        mCurrentTab, holder.getAdapterPosition(),true, mCallback);

            }
        });

    }

    @Override
    public int getItemCount() {
        return nasaDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.user_image);
        }
    }

}
