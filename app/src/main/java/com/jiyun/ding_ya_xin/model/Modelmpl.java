package com.jiyun.ding_ya_xin.model;

import android.util.Log;

import com.jiyun.ding_ya_xin.api.ApiService;
import com.jiyun.ding_ya_xin.bean.HomeBean;
import com.jiyun.ding_ya_xin.callback.CallBack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Modelmpl implements Model{

    private static final String TAG = "Modelmpl";
    @Override
    public void updateData(final CallBack callBack) {
        //Retrofit+Rxjava解析
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.Url)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean bean) {
                        callBack.updateSuccess(bean);
                        Log.d("aaa", "onNext: "+bean.getData().getDatas());
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.updateFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
