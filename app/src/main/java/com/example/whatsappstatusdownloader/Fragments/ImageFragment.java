package com.example.whatsappstatusdownloader.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappstatusdownloader.Adapters.ImageAdapter;
import com.example.whatsappstatusdownloader.Models.StatusModel;
import com.example.whatsappstatusdownloader.R;
import com.example.whatsappstatusdownloader.Utils.MyConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends Fragment {

    public RecyclerView recyclerView;
    public ProgressBar progressBar;

    ArrayList<StatusModel> imageModelArrayList;
    Handler handler = new Handler();
    ImageAdapter imageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarImage);

        imageModelArrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        
        getStatus();
    }

    private void getStatus() {
        if(MyConstants.STATUS_DIRECTORY.exists()){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    File [] statusFiles = MyConstants.STATUS_DIRECTORY.listFiles();

                    if(statusFiles != null && statusFiles.length > 0){

                        Arrays.sort(statusFiles);

                        for (final File statusFile:statusFiles){
                            StatusModel statusModel = new StatusModel(statusFile,
                                    statusFile.getName(),statusFile.getAbsolutePath());

                            statusModel.setThumbnail(getThumbnail(statusModel));

                            if(!statusModel.isVideo()){
                                imageModelArrayList.add(statusModel);
                            }
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);

                                imageAdapter = new ImageAdapter(getContext(),imageModelArrayList,ImageFragment.this);
                                recyclerView.setAdapter(imageAdapter);
                                imageAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(getContext(),"Dir does not exist",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private Bitmap getThumbnail(StatusModel statusModel) {
        if(statusModel.isVideo()){
            return ThumbnailUtils.createVideoThumbnail(statusModel.getFile().getAbsolutePath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
        }
        else {
            return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(statusModel.getFile().getAbsolutePath()),
                    MyConstants.THUMB_SIZE,
                    MyConstants.THUMB_SIZE);
        }
    }

    public void downloadImage(StatusModel statusModel) throws IOException {

        File file = new File(MyConstants.APP_DIR);

        if(file.exists()){
            file.mkdir();
        }
        File destFile = new File(file + File.separator + statusModel.getTitle());

        if(destFile.exists()){
            destFile.delete();
        }

        copyFile(statusModel.getFile(), destFile);

        Toast.makeText(getContext(),"Download complete",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);

    }

    private void copyFile(File file, File destFile) throws IOException {

        if(!destFile.getParentFile().exists()){
            destFile.getParentFile().mkdir();
        }
        if(!destFile.exists()){
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        source = new FileInputStream(file).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source,0,source.size());

        source.close();
        destination.close();
    }
}
