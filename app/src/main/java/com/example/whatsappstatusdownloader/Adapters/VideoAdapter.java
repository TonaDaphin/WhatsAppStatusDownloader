package com.example.whatsappstatusdownloader.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappstatusdownloader.Fragments.ImageFragment;
import com.example.whatsappstatusdownloader.Fragments.VideoFragment;
import com.example.whatsappstatusdownloader.Models.StatusModel;
import com.example.whatsappstatusdownloader.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<StatusModel> videoList;
    Context context;
    VideoFragment videoFragment;

    public VideoAdapter(Context context, List<StatusModel> videoList, VideoFragment videoFragment){

        this.context = context;
        this.videoList = videoList;
        this.videoFragment = videoFragment;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        StatusModel statusModel = videoList.get(position);
        holder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivThumbnailImgView;
        public ImageButton ivImgDownload;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            final View view = itemView;
            ivThumbnailImgView = (ImageView) view.findViewById(R.id.ivThumbnail);
            ivImgDownload = (ImageButton) view.findViewById(R.id.ivSaveToGallery);

            ivImgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel statusModel = videoList.get(getAdapterPosition());

                    if(statusModel != null){
                        try {
                            videoFragment.downloadVideo(statusModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
