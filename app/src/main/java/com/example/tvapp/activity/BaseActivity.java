package com.example.tvapp.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity的基类
 * Created by liumj on 2015/6/25.
 */
public abstract class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        initView();
        initData();
        initListener();
    }

    /**
     * 初始化监听器
     */
    protected abstract void initListener();


    /**
     * 初始化控件，所有findViewById在此处理
     */
    protected abstract void initView();

    /**
     * 初始化数据，对控件所需要的数据进行初始化处理
     */
    protected abstract void initData();

    @Override
    protected void onResume() {
        //强制横屏
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
