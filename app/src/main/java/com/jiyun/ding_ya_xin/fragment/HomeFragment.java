package com.jiyun.ding_ya_xin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.ding_ya_xin.R;
import com.jiyun.ding_ya_xin.UpDownActivity;
import com.jiyun.ding_ya_xin.adapter.HomeAdapter;
import com.jiyun.ding_ya_xin.bean.HomeBean;
import com.jiyun.ding_ya_xin.iview.IView;
import com.jiyun.ding_ya_xin.persenter.PersenterImpl;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IView, HomeAdapter.SetOnClick {


    private View view;
    private RecyclerView mRv;
    private ArrayList<HomeBean.DataBean.DatasBean> list;
    private HomeAdapter adapter;
    private PersenterImpl persenter;
    private static final String TAG = "HomeFragment";
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        persenter = new PersenterImpl(this);
        persenter.update();
    }

    private void initView(View inflate) {
        mRv = (RecyclerView) inflate.findViewById(R.id.rv);
        //布局管理器
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getActivity());
        //绑定适配器
        mRv.setAdapter(adapter);
        adapter.OnClick(this);
    }

    @Override
    public void updateUI(HomeBean bean) {
        list.addAll(bean.getData().getDatas());
        adapter.setList(list);
        adapter.notifyDataSetChanged();
        Log.d("aaa", "updateUI: "+bean.getData().getDatas());
    }

    @Override
    public void updateError(String result) {
        Log.d(TAG, "updateError: "+result);
    }

    @Override
    public void SetOn(int position, HomeBean.DataBean.DatasBean bean) {
        startActivity(new Intent(getActivity(),UpDownActivity.class));
    }
}
