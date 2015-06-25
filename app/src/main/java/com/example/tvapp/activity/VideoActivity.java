package com.example.tvapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.tvapp.R;
import com.example.tvapp.adapter.VideoAdapter;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Info;
import com.example.tvapp.bean.Video;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends BaseActivity {

    private TwoWayGridView gridView;
    private VideoAdapter myAdapter;
    private Context context;
    private List<Info> list;
    private List<Video> videoList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.video_gridview);




    }

    @Override
    protected void initListener() {
        gridView.setOnItemClickListener(new TwoWayAdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {

                intent.setClass(VideoActivity.this, VideoInfoActivity.class);
                intent.putExtra("url", videoList.get(position).getUrl());
                startActivity(intent);

            }
        });
        gridView.setOnItemSelectedListener(new TwoWayAdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(TwoWayAdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(TwoWayAdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initView() {
        gridView = (TwoWayGridView) findViewById(R.id.video_gridview);
    }

    @Override
    protected void initData() {
        Drawable drawable = getResources().getDrawable(R.drawable.state_focus);
        gridView.setSelector(drawable);
        list = new ArrayList<Info>();
        intent = new Intent();
        ProgressDlgUtil.showProgressDlg("加载中...", VideoActivity.this);
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET, TVUrl.VideoGet, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException error, String msg) {
                ProgressDlgUtil.stopProgressDlg();
                Throwable throwable = error.getCause();
                if (throwable.toString().contains("ConnectException")) {
                    MyToast.showS(getApplicationContext(), "断网了!");
                } else if (throwable.toString().contains("ConnectTimeoutException")) {
                    MyToast.showS(getApplicationContext(), "超时了!");
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                ProgressDlgUtil.stopProgressDlg();
                Gson gson = new Gson();
                String result = responseInfo.result;

                try {
                    JSONObject object = new JSONObject(result);
                    String optString2 = object.optString("result");
                    if (optString2.equals("success")) {

                        String optString = object.optString("retarr");
                        videoList = gson.fromJson(optString, new TypeToken<List<Video>>() {
                        }.getType());


                        myAdapter = new VideoAdapter(getApplicationContext(), videoList);

                        gridView.setAdapter(myAdapter);

                    } else {
                        MyToast.showS(getApplicationContext(), "访问数据出错");
                        return;
                    }
                } catch (JSONException e) {
                    MyToast.showS(getApplicationContext(), "访问数据出错");
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });

    }
}
