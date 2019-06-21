package com.campusmonk.vikas.msrit;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class my_async_download_task extends AsyncTask<String, Float, String> {
    Builder DownloadProgressNotification;
    NotificationManager DownloadProgressNotificationManager;
    Long ElapsedTime = Long.valueOf(0);
    Long StartTime = Long.valueOf(0);
    Activity activity;
    BufferedInputStream bufferedInputStream;
    Editor editor;
    File file;
    double file_size = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* renamed from: i */
    int f16i = 1;
    InputStream inputStream;
    OutputStream outputStream;
    ProgressDialog progressDialog;
    int random_id;
    SharedPreferences sharedPreferences;
    String subject_name;
    String unit_name;
    URL url1;
    HttpURLConnection urlConnection;

    /* renamed from: com.campusmonk.vikas.msrit.my_async_download_task$1 */
    class C04231 implements OnClickListener {
        C04231() {
        }

        public void onClick(View v) {
            Intent intent = new Intent("android.intent.action.VIEW");
            if (VERSION.SDK_INT >= 24) {
                intent.setFlags(1);
                intent.setDataAndType(FileProvider.getUriForFile(my_async_download_task.this.activity.getApplicationContext(), my_async_download_task.this.activity.getCallingPackage(), new File(Uri.parse(my_async_download_task.this.sharedPreferences.getString(my_async_download_task.this.unit_name + my_async_download_task.this.subject_name, " ")).getPath())), "application/pdf");
                my_async_download_task.this.activity.startActivity(intent);
                return;
            }
            intent.setDataAndType(Uri.parse(my_async_download_task.this.sharedPreferences.getString(my_async_download_task.this.unit_name + my_async_download_task.this.subject_name, " ")), "application/pdf");
            my_async_download_task.this.activity.startActivity(intent);
        }
    }

    public my_async_download_task(String unit_name, String subject_name, Activity activity) {
        this.unit_name = unit_name;
        this.subject_name = subject_name;
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences("my_preferences", 0);
        this.editor = this.sharedPreferences.edit();
        this.DownloadProgressNotification = new Builder(activity.getApplicationContext());
        this.DownloadProgressNotificationManager = (NotificationManager) activity.getSystemService("notification");
        this.DownloadProgressNotification.setContentTitle("Downloading " + unit_name);
        this.DownloadProgressNotification.setContentText("Downloading in progress");
        this.DownloadProgressNotification.setSmallIcon(C0418R.drawable.ic_picture_as_pdf_black_24dp);
        this.random_id = (unit_name + "" + subject_name).hashCode();
    }

    protected void onPreExecute() {
        this.progressDialog = new ProgressDialog(this.activity);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setMessage("Downloading " + this.unit_name);
        this.progressDialog.setMax(100);
        this.progressDialog.setCancelable(false);
    }

    protected void onProgressUpdate(Float... values) {
        this.progressDialog.setProgress(Math.round(values[0].floatValue()));
        if (this.file_size != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            if (this.ElapsedTime.longValue() > 100 || this.f16i == 1) {
                this.f16i = 0;
                this.DownloadProgressNotification.setProgress(100, Math.round(values[0].floatValue()), false);
                this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
                this.StartTime = Long.valueOf(new Date().getTime());
                this.ElapsedTime = Long.valueOf(0);
            } else {
                this.ElapsedTime = Long.valueOf(new Date().getTime() - this.StartTime.longValue());
            }
            this.progressDialog.setMessage("Downloading " + this.unit_name + " Size " + this.file_size + "mb");
            return;
        }
        Long StartTime = Long.valueOf(0);
        Long ElapsedTime;
        if (Long.valueOf(0).longValue() > 100 || this.f16i == 1) {
            this.f16i = 0;
            this.DownloadProgressNotification.setProgress(100, 100, true);
            this.DownloadProgressNotification.setContentText("Downloaded:" + values[1] + "mb");
            this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
            StartTime = Long.valueOf(new Date().getTime());
            ElapsedTime = Long.valueOf(0);
        } else {
            ElapsedTime = Long.valueOf(new Date().getTime() - StartTime.longValue());
        }
        this.progressDialog.setMessage("File size is greater than 15mb.Progress may not be show.Please wait....         Downloaded=" + values[1] + "mb");
    }

    protected void onPostExecute(String s) {
        this.progressDialog.dismiss();
        this.f16i = 1;
        this.StartTime = Long.valueOf(0);
        this.ElapsedTime = Long.valueOf(0);
        this.DownloadProgressNotification.setProgress(100, 100, false);
        this.DownloadProgressNotification.setContentText("Download Complete");
        this.DownloadProgressNotificationManager.notify(this.random_id, this.DownloadProgressNotification.build());
        if (!this.sharedPreferences.getString(this.unit_name + this.subject_name, "not_set").equals("not_set")) {
            Snackbar.make(this.activity.getCurrentFocus(), (CharSequence) "Download Complete", 0).setAction((CharSequence) "open", new C04231()).show();
        }
    }

    @RequiresApi(api = 24)
    protected String doInBackground(String... strings) {
        try {
            this.url1 = new URL(strings[0]);
            this.urlConnection = (HttpURLConnection) this.url1.openConnection();
            this.file_size = ((double) this.urlConnection.getContentLength()) / 1000000.0d;
            Log.v("mm", Math.round(this.file_size * 100.0d) + "");
            this.file_size = ((double) Math.round(this.file_size * 1000.0d)) / 1000.0d;
            this.urlConnection.connect();
            this.inputStream = this.urlConnection.getInputStream();
            this.bufferedInputStream = new BufferedInputStream(this.inputStream);
            File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + "MsritOne" + "/" + this.subject_name + ""));
            file.mkdirs();
            File f = new File(file, this.unit_name + ".pdf");
            this.outputStream = new FileOutputStream(f);
            byte[] data = new byte[1024];
            int total = 0;
            while (true) {
                int count = this.bufferedInputStream.read(data);
                if (count == -1) {
                    break;
                }
                total += count;
                publishProgress(new Float[]{Float.valueOf(((float) (total * 100)) / ((float) length)), Float.valueOf(((float) Math.round(((float) total) / 10000.0f)) / 100.0f)});
                this.outputStream.write(data, 0, count);
            }
            if (f.exists()) {
                this.editor.putString(this.unit_name + this.subject_name, Uri.fromFile(f).toString());
                this.editor.commit();
            }
        } catch (MalformedURLException e) {
            this.progressDialog.dismiss();
            e.printStackTrace();
        } catch (IOException e2) {
            this.progressDialog.dismiss();
            e2.printStackTrace();
        }
        return null;
    }
}
