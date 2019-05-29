package com.jiyun.ding_ya_xin.callback;

import com.jiyun.ding_ya_xin.bean.HomeBean;

public interface CallBack {
    void updateSuccess(HomeBean bean);
    void updateFailed(String result);
}
