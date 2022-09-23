package com.obvious.nasagallery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.obvious.nasagallery.NasaGalleryApp;
import com.obvious.nasagallery.R;
import com.obvious.nasagallery.data.model.NasaData;
import com.obvious.nasagallery.util.BaseFragment;
import com.obvious.nasagallery.util.ConnectivityReceiver;
import com.obvious.nasagallery.util.NasaGalleryConstants;

import org.w3c.dom.Text;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> implements NasaGalleryConstants {

    Context mContext;

    String mCurrentTab;
    BaseFragment.FragmentInteractionCallback mCallback;

    List<NasaData> nasaDataList = null;

    public DetailAdapter(Context context, String currentTab,
                                    BaseFragment.FragmentInteractionCallback interactionCallback) {
        this.mContext = context;
        this.mCurrentTab = currentTab;
        this.mCallback = interactionCallback;

    }

    public void setData( List<NasaData> nasaDataList){
        this.nasaDataList = nasaDataList;
    }



    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_detail_item, viewGroup, false);
        return new DetailViewHolder(view);
    }


    @Override
    public void onBindViewHolder(DetailViewHolder holder, @SuppressLint("RecyclerView") int position) {

        NasaData nasaData = nasaDataList.get(holder.getAdapterPosition());

        holder.tvHeader.setText(nasaData.getTitle());
        holder.tvDescription.setText(nasaData.getExplanation());

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
                    .displayImage(nasaData.getUrl(),
                            new ImageViewAware(holder.mImageView), options);

        } else {
            holder.mImageView.setImageResource(R.drawable.image_placeholder);
        }

    }

    @Override
    public int getItemCount() {
        return nasaDataList.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {

        AppCompatImageView mImageView;
        TextView tvHeader;
        TextView tvDescription;

        public DetailViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView);
            tvHeader = itemView.findViewById(R.id.text_header);
            tvDescription = itemView.findViewById(R.id.text_body);

        }

    }

}
