package com.example.tvapp.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.adapter.ExamTestAdapter;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.ExamQues;
import com.example.tvapp.bean.ExamResult;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @version V1.0
 * @类名称: PsyTestInfoActivity
 * @类描述: TODO 心理测试详情
 * @创建人：liuml
 * @创建时间：2015-6-4 下午12:10:00
 * @备注：
 */
@ContentView(R.layout.psy_test_list_activity)
public class PsyTestInfoActivity extends BaseActivity {

    private ListView lv_test;
    private TextView tv_test;
    private String examid, classname;
    private List<ExamQues> QuesList;
    private ExamResult Result;
    private RadioGroup radioGroup1;
    private ExamTestAdapter Examadapter;
    private TextView tv;
    private Button button;
    private boolean first = true;
    private int content = 0;
    private String sum;


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        examid = startIntent.getStringExtra("examid");
        classname = startIntent.getStringExtra("classname");
        QuesList = new ArrayList<ExamQues>();
        Result = new ExamResult();
        ProgressDlgUtil.showProgressDlg("加载中...", PsyTestInfoActivity.this);
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("examid", examid);
        HttpUtils http = new HttpUtils();
        http.send(HttpMethod.GET, TVUrl.ExamInfo, params, new RequestCallBack<String>() {

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
                        Result = gson.fromJson(result, ExamResult.class);
                        String questions = object.optString("questions");
                        QuesList = gson.fromJson(questions, new TypeToken<List<ExamQues>>() {
                        }.getType());
                        DataSet();
                    } else {
                        MyToast.showS(getApplicationContext(), "访问数据出错");
                        return;
                    }
                } catch (JSONException e) {
                    MyToast.showS(getApplicationContext(), "访问数据出错");
                    e.printStackTrace();
                }
            }

        });

    }

    private void DataSet() {

        Examadapter = new ExamTestAdapter(getApplicationContext(), QuesList);

        View view = View.inflate(getApplicationContext(), R.layout.psy_test_list_foot, null);

        button = (Button) view.findViewById(R.id.bt_foot);
        tv = (TextView) view.findViewById(R.id.tv_foot);

        button.setText("测试完成");
        lv_test.addFooterView(view);
        lv_test.setAdapter(Examadapter);
        tv_test.setText(Result.getTitle());

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (first) {
                    first = false;
                    tv.setVisibility(View.VISIBLE);
                    lv_test.setSelection(lv_test.getBottom());
                    tv.setText("超级“懒鬼”:虽然生活中的你也许并不是懒惰成性，但在打理家庭财务方面，绝对属于超级“懒”人，对于理财完全缺乏意识和想法。");

                } else {
                    finish();
                }
            }
        });

    }

    @Override
    protected void initView() {
        ViewUtils.inject(this);
        tv_test = (TextView) findViewById(R.id.tv_test);
        lv_test = (ListView) findViewById(R.id.lv_test);
        lv_test.setItemsCanFocus(true);
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);

    }
}
