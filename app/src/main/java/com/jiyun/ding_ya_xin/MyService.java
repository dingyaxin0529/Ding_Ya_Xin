package com.jiyun.ding_ya_xin;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UpDownActivity upDownActivity = new UpDownActivity();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
