package com.jiyun.ding_ya_xin.persenter;

import com.jiyun.ding_ya_xin.bean.HomeBean;
import com.jiyun.ding_ya_xin.callback.CallBack;
import com.jiyun.ding_ya_xin.iview.IView;
import com.jiyun.ding_ya_xin.model.Model;
import com.jiyun.ding_ya_xin.model.Modelmpl;

public class PersenterImpl implements Persenter{
    Model model;
    IView iView;

    public PersenterImpl( IView iView) {
        this.model = new Modelmpl();
        this.iView = iView;
    }

    @Override
    public void update() {
        model.updateData(new CallBack() {
            @Override
            public void updateSuccess(HomeBean bean) {
                iView.updateUI(bean);
            }

            @Override
            public  void updateFailed(String result) {
                iView.updateError(result);
            }
        });
    }
}
