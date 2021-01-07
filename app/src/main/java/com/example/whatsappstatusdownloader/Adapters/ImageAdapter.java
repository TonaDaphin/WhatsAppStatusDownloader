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
import com.example.whatsappstatusdownloader.Models.StatusModel;
import com.example.whatsappstatusdownloader.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<StatusModel> imageList;
    Context context;
    ImageFragment imageFragment;

    public ImageAdapter(Context context,List<StatusModel> imageList,ImageFragment imageFragment){

        this.context = context;
        this.imageList = imageList;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        StatusModel statusModel = imageList.get(position);
        holder.ivThumbnailImgView.setImageBitmap(statusModel.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivThumbnailImgView;
        public ImageButton ivImgDownload;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            View view = itemView;
            ivThumbnailImgView = (ImageView) view.findViewById(R.id.ivThumbnail);
            ivImgDownload = (ImageButton) view.findViewById(R.id.ivSaveToGallery);

            ivImgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel statusModel = imageList.get(getAdapterPosition());

                    if(statusModel != null){
                        try {
                            imageFragment.downloadImage(statusModel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

}
