package com.jiyun.ding_ya_xin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jiyun.ding_ya_xin.adapter.VpAdapter;
import com.jiyun.ding_ya_xin.fragment.HomeFragment;
import com.jiyun.ding_ya_xin.fragment.MyFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVp;
    private TabLayout mTab;
    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new MyFragment());
        mTab.addTab(mTab.newTab().setText("首页").setIcon(R.drawable.selet1));
        mTab.addTab(mTab.newTab().setText("我的").setIcon(R.drawable.selet2));
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), list);
        //绑定Viewpager适配器
        mVp.setAdapter(adapter);
        mTab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVp));
        mVp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
    }
}
