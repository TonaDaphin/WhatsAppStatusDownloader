package com.example.whatsappstatusdownloader.Utils;

import android.os.Environment;

import java.io.File;

public class MyConstants {

    public static final File STATUS_DIRECTORY =
            new File (Environment.getExternalStorageDirectory()+
                    File.separator + "WhatsApp/Media/.Statuses");

    public static final  String APP_DIR = Environment.getExternalStorageDirectory()
            + File.separator + "WhatsAppStatusDownloader";

    public static final int THUMB_SIZE = 128;
}
