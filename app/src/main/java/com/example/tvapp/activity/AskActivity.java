package com.example.tvapp.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Doctor;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @version V1.0
 * @类名称: AskActivity
 * @类描述: TODO 咨询师详细
 * @创建人：liuml
 * @创建时间：2015-6-10 下午2:32:46
 * @备注：
 */
public class AskActivity extends BaseActivity {

    private HttpUtils http;
    private ImageView iv_head;
    private TextView tv_name, tv_age, tv_jibie, tv_price, tv_vipprice, tv_goodat, tv_info;
    private String uid, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_info);
        http = new HttpUtils();
        uid = getIntent().getStringExtra("uid");
        age = getIntent().getStringExtra("age");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        iv_head = (ImageView) findViewById(R.id.ImageView01);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_jibie = (TextView) findViewById(R.id.tv_jibie);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_vipprice = (TextView) findViewById(R.id.tv_vipprice);
        tv_goodat = (TextView) findViewById(R.id.tv_goodat);
        tv_info = (TextView) findViewById(R.id.tv_info);

    }

    @Override
    protected void initData() {
        ProgressDlgUtil.showProgressDlg("加载中...", AskActivity.this);
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("uid", uid);
        http.send(HttpMethod.GET, TVUrl.DoctorGet, params, new RequestCallBack<String>() {

            private Doctor doctor;

            @Override
            public void onFailure(HttpException error, String arg1) {
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
                String result = responseInfo.result;

                try {
                    JSONObject object = new JSONObject(result);
                    String optString = object.optString("result");
                    if (optString.equals("success")) {

                        Gson gson = new Gson();

                        doctor = gson.fromJson(result, Doctor.class);

                        tv_name.setText(doctor.getName());

                        tv_jibie.setText(doctor.getGrade());
                        tv_price.setText(doctor.getPrice() + "/次");
                        tv_vipprice.setText(doctor.getVipprice() + "/次");
                        tv_info.setText(doctor.getIntro());
                        tv_goodat.setText(doctor.getGoodat());
                        BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
                        bitmapUtils.display(iv_head, TVUrl.DefUrl + doctor.getImage());
                        if (age != null) {
                            tv_age.setText(age);
                        }
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

}
