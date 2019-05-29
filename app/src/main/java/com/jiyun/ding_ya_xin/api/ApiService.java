package com.jiyun.ding_ya_xin.api;

import com.jiyun.ding_ya_xin.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    /**
     * 首页接口: https://www.wanandroid.com/project/list/1/json?cid=294, 请求方式:get
     */
    public String Url="https://www.wanandroid.com/";
    @GET("project/list/1/json?cid=294")
    Observable<HomeBean>getList();
}
