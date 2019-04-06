package com.example.trackingsurat.presenter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;

public class DownloadPresenter {
    private Context context;
    public DownloadPresenter(Context context){
        this.context = context;
    }
    File file;

    public void downloadFile(){
        file = new File(context.getExternalFilesDir(null),"Dummy");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://speedtest.ftp.otenet.gr/files/test10Mb.db"))
                    .setTitle("Dummy File")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        }else {
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse("http://speedtest.ftp.otenet.gr/files/test10Mb.db"))
                    .setTitle("Dummy File")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                    .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        }

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

    }
}
