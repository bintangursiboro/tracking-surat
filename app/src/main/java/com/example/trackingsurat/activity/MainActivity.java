package com.example.trackingsurat.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.trackingsurat.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    RelativeLayout linkDownload;
    RelativeLayout linkYt;
    DownloadManager downloadManager;
    private long refid;
    private Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = findViewById(R.id.track);
        linkDownload = findViewById(R.id.layout_download);
        linkYt = findViewById(R.id.layout_youtube);
//        initSharedPreference();
        preferences = getSharedPreferences("Preference", MODE_PRIVATE);
        Log.e("preferences", String.valueOf(preferences.getBoolean("isLogin",false)));
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (!isStoragePermissionGranted()){

        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TrackingActivity.class));
            }
        });
        linkDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginDownload();
            }
        });
        linkYt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCp0ul1WulpgHNRELYGhNHtw"));
                startActivity(intent);
            }
        });


        //Download Manager
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        Download_Uri = Uri.parse("http://www.pajak.go.id/sites/default/files/Formulir%20SPT%201771-TKB_0.pdf");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences preferences = getSharedPreferences("Preference",MODE_PRIVATE);
        if (preferences.getBoolean("isLogin",false)){
            Log.e("wew",String.valueOf(preferences.getBoolean("isLogin",false)));
            getMenuInflater().inflate(R.menu.menu_admin, menu);
        }else {
            getMenuInflater().inflate(R.menu.menu_default,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("Preference",MODE_PRIVATE);
        int itemId = item.getItemId();
        if (preferences.getBoolean("isLogin", false)){
            if (itemId == R.id.action_favorite){
                startActivity(new Intent(MainActivity.this, UploadActivity.class));
                return true;
            }else if (itemId == R.id.action_logout){
                initSharedPreference();
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        }else {
            if (itemId == R.id.action_favorite){
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            }
        }



        return super.onOptionsItemSelected(item);
    }

    //untuk downloadmanager
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }





    BroadcastReceiver onComplete = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {




            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty())
            {


                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("FormulirSPT171")
                                .setContentText("All Download completed");


                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };


    void initSharedPreference(){
        editor =  getSharedPreferences("Preference",MODE_PRIVATE).edit();
        preferences = getSharedPreferences("Preference", MODE_PRIVATE);
        editor.putBoolean("isLogin",false);
        editor.apply();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

        unregisterReceiver(onComplete);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){


            // permission granted

        }
    }

    void beginDownload(){
        list.clear();

        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading " + "surat-spt-171" + ".pdf");
        request.setDescription("Downloading " + "surat-spt-171" + ".pdf");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Download/"  + "/" + "surat-spt-171" + ".pdf");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        refid = downloadManager.enqueue(request);


        Log.e("OUT", "" + refid);

        list.add(refid);
    }

}
