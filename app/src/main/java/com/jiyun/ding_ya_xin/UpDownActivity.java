package com.jiyun.ding_ya_xin;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpDownActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 下载接口: http://cdn.banmi.com/banmiapp/apk/banmi_330.apk,请求方式:get
     */

    /**
     * Button
     */
    private Button mButton;
    /**
     * TextView
     */
    private TextView mTextView;
    private File sd;
    private NotificationManager manager;
    private long mProgress;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_down);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        EventBus.getDefault().register(this);
        initView();
        initSD();
    }




    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                httpxiazai();
                break;
        }
    }
    //apk下载
    private void httpxiazai() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://cdn.banmi.com/banmiapp/apk/banmi_330.apk");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = con.getInputStream();
                        int max = con.getContentLength();
                        saveFile(inputStream, sd + "/" + "abc789.apk", max);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public class ProgressEvent {
        public int progress;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpData(ProgressEvent progressEvent) {

        mTextView.setText("当前下载进度："+progressEvent.progress + "%");
        Log.d(TAG, "onUpData: "+progressEvent.progress+"%");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private static final String TAG = "CeShiActivity";
    private void saveFile(InputStream inputStream, String s, int max) {
        long count = 0;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(s));
            byte[] bytes = new byte[1024 * 10];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, length);
                count += length;
                mProgress = count * 100 / max;
                mProgressBar.setProgress((int) mProgress);
                ProgressEvent progressEvent = new ProgressEvent();
                progressEvent.progress = (int) mProgress;
                EventBus.getDefault().post(progressEvent);
               // Log.d(TAG, "saveFile: progress" + count + "   max:" + max);
            }
            inputStream.close();
            fileOutputStream.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(UpDownActivity.this, "下载已完成", Toast.LENGTH_SHORT).show();

                    String channelId = "1";
                    String channelName = "default";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
                        manager.createNotificationChannel(channel);
                    }
                    Intent intent = new Intent(UpDownActivity.this, MainActivity.class);
                    PendingIntent activity = PendingIntent.getActivity(UpDownActivity.this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification build = new NotificationCompat.Builder(UpDownActivity.this, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentText("已下载完成")//设置内容
                            .setContentTitle("下载apk")//设置标题
                            .setAutoCancel(true)//点击自动消失
                            .build();
                    manager.notify(100, build);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSD() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            openSd();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openSd();
        }
    }

    private void openSd() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sd = Environment.getExternalStorageDirectory();
        }
    }
}
