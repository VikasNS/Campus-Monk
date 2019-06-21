package com.campusmonk.vikas.msrit;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class DownloadIntentService extends IntentService {
    Context ApplicationContext;
    Builder DownloadProgressNotification;
    NotificationManager DownloadProgressNotificationManager;
    URL DownloadURL;
    String DownloadUrl;
    Uri DownloadedFileUri;
    Editor Editor__UriOfDownloadedFiles;
    Long ElapsedTime = Long.valueOf(0);
    SharedPreferences SharedPreference_UriOfDownloadedFiles;
    Long StartTime = Long.valueOf(0);
    String SubjectName;
    String UnitName;
    BufferedInputStream bufferedInputStream;
    File file;
    double file_size = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* renamed from: i */
    int f11i = 1;
    InputStream inputStream;
    OutputStream outputStream;
    int random_id;
    HttpURLConnection urlConnection;

    public DownloadIntentService(String name) {
        super(name);
    }

    public DownloadIntentService() {
        super("downloadService");
    }

    @RequiresApi(api = 16)
    protected void onHandleIntent(@Nullable Intent intent) {
        this.ApplicationContext = getApplicationContext();
        this.SharedPreference_UriOfDownloadedFiles = this.ApplicationContext.getSharedPreferences("my_preferences", 0);
        this.Editor__UriOfDownloadedFiles = this.SharedPreference_UriOfDownloadedFiles.edit();
        this.SubjectName = intent.getStringExtra("SubjectName");
        this.UnitName = intent.getStringExtra("UnitName");
        this.random_id = this.SharedPreference_UriOfDownloadedFiles.getInt("random_id", 0);
        this.Editor__UriOfDownloadedFiles.putString(this.UnitName + this.SubjectName, "Downloading");
        this.Editor__UriOfDownloadedFiles.commit();
        this.ApplicationContext.sendBroadcast(new Intent("Download State Update"));
        this.Editor__UriOfDownloadedFiles.putInt("random_id", this.random_id + 1);
        this.Editor__UriOfDownloadedFiles.commit();
        this.DownloadUrl = intent.getStringExtra("DownloadUrl");
        this.DownloadProgressNotification = new Builder(this.ApplicationContext);
        this.DownloadProgressNotificationManager = (NotificationManager) this.ApplicationContext.getSystemService("notification");
        this.DownloadProgressNotification.setContentTitle("Downloading " + this.UnitName);
        this.DownloadProgressNotification.setContentText("Starting Download");
        this.DownloadProgressNotification.setSmallIcon(C0418R.drawable.ic_picture_as_pdf_black_24dp);
        this.DownloadProgressNotification.setTicker("Download Started");
        this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
        DownloadFile();
        this.f11i = 1;
    }

    private void DownloadFile() {
        try {
            this.DownloadURL = new URL(this.DownloadUrl);
            this.urlConnection = (HttpURLConnection) this.DownloadURL.openConnection();
            this.file_size = ((double) this.urlConnection.getContentLength()) / 1000000.0d;
            this.file_size = ((double) Math.round(this.file_size * 1000.0d)) / 1000.0d;
            this.urlConnection.connect();
            this.inputStream = this.urlConnection.getInputStream();
            this.bufferedInputStream = new BufferedInputStream(this.inputStream);
            File CampusMonkDirectory = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "CampusMonk" + "/" + this.SubjectName + ""));
            CampusMonkDirectory.mkdirs();
            File DownloadingFile = new File(CampusMonkDirectory, this.UnitName + ".pdf");
            this.outputStream = new FileOutputStream(DownloadingFile);
            byte[] data = new byte[1024];
            int total = 0;
            while (true) {
                int count = this.bufferedInputStream.read(data);
                if (count == -1) {
                    break;
                }
                total += count;
                PublishProgress(((float) (total / 10000)) / ((float) this.file_size), ((float) Math.round(((float) total) / 10000.0f)) / 100.0f);
                Log.v("File Size", this.file_size + "");
                Log.v("total", total + "");
                Log.v("Progress", (((float) (total / 10000)) / ((float) this.file_size)) + "");
                this.outputStream.write(data, 0, count);
            }
            if (DownloadingFile.exists()) {
                this.DownloadedFileUri = Uri.fromFile(DownloadingFile);
                this.Editor__UriOfDownloadedFiles.putString(this.UnitName + this.SubjectName, this.DownloadedFileUri.toString());
                this.Editor__UriOfDownloadedFiles.commit();
            }
            SetUpOnNotificationClicked();
            PostDownload();
        } catch (MalformedURLException e) {
            HandleError();
            e.printStackTrace();
        } catch (IOException e2) {
            HandleError();
            e2.printStackTrace();
        }
    }

    private void SetUpOnNotificationClicked() {
        Intent OpenPdfReaderIntent = new Intent("android.intent.action.VIEW");
        OpenPdfReaderIntent.setFlags(1);
        OpenPdfReaderIntent.setDataAndType(this.DownloadedFileUri, "application/pdf");
        this.DownloadProgressNotification.setContentIntent(PendingIntent.getActivity(this.ApplicationContext, this.SharedPreference_UriOfDownloadedFiles.getInt("random_id", 0), OpenPdfReaderIntent, 0));
        this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
        Log.v("File Uri Link", this.SharedPreference_UriOfDownloadedFiles.getString(this.UnitName + this.SubjectName, "not_set"));
        this.ApplicationContext.sendBroadcast(new Intent("Download State Update"));
        Log.v("Broadcastmessage status", "Sent");
    }

    private void HandleError() {
        this.ApplicationContext.sendBroadcast(new Intent("Download State Update"));
        this.Editor__UriOfDownloadedFiles.putString(this.UnitName + this.SubjectName, "not_set");
        this.Editor__UriOfDownloadedFiles.commit();
        this.DownloadProgressNotification.setContentTitle("Unable to Download");
        this.DownloadProgressNotification.setContentText("");
        this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
    }

    private void PostDownload() {
        this.DownloadProgressNotification.setProgress(100, 100, false);
        this.DownloadProgressNotification.setTicker("Download Completed");
        this.DownloadProgressNotification.setContentTitle("Download Completed");
        this.DownloadProgressNotification.setContentText(this.UnitName + " " + this.SubjectName);
        this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
    }

    private void PublishProgress(float... values) {
        if (this.file_size != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            Log.v("Entered", "known size");
            if (this.ElapsedTime.longValue() > 200 || this.f11i == 1) {
                this.f11i = 0;
                this.DownloadProgressNotification.setProgress(100, Math.round(values[0]), false);
                this.DownloadProgressNotification.setContentText("Downloading");
                this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
                this.StartTime = Long.valueOf(new Date().getTime());
                this.ElapsedTime = Long.valueOf(0);
                return;
            }
            this.ElapsedTime = Long.valueOf(new Date().getTime() - this.StartTime.longValue());
            return;
        }
        Log.v("Entered", "Unknown size");
        if (this.ElapsedTime.longValue() > 200 || this.f11i == 1) {
            this.f11i = 0;
            this.DownloadProgressNotification.setProgress(100, 100, true);
            this.DownloadProgressNotification.setContentText("Downloaded:" + values[1] + "mb");
            this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
            this.StartTime = Long.valueOf(new Date().getTime());
            this.ElapsedTime = Long.valueOf(0);
            return;
        }
        this.ElapsedTime = Long.valueOf(new Date().getTime() - this.StartTime.longValue());
    }
}
