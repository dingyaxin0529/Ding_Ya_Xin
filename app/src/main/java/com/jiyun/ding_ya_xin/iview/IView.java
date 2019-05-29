package com.jiyun.ding_ya_xin.iview;

import com.jiyun.ding_ya_xin.bean.HomeBean;

public interface IView {
    void updateUI(HomeBean bean);
    void updateError(String result);
}
