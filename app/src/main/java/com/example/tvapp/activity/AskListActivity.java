package com.example.tvapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.tvapp.R;
import com.example.tvapp.adapter.AskAdapter;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Doctor;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayGridView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import static com.jess.ui.TwoWayAdapterView.OnItemClickListener;

/**
 * 
 * @类名称: AskListActivity 
 * @类描述: TODO 咨询师列表
 * @创建人：liuml 
 * @创建时间：2015-6-10 下午2:33:05  
 * @备注：      
 * @version V1.0
 */
@ContentView(R.layout.ask_gridview)
@SuppressLint("SimpleDateFormat")
public class AskListActivity extends BaseActivity {
	@ViewInject(R.id.listView_AllApp)
	private TwoWayGridView gridView;
	private AskAdapter myAdapter;
	private Context context;
	private List<Doctor> doclist;
	private Intent intent;


	@Override
	protected void initListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {


			@SuppressWarnings("deprecation")
			@Override
			public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {

				intent.setClass(AskListActivity.this, AskActivity.class);
				Doctor doctor = doclist.get(position);

				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);

				String birthday = doctor.getBirthday();
				if (!birthday.equals("") & birthday != null) {

					String substring = birthday.substring(0, 4);
					int parseInt = Integer.parseInt(substring);

					intent.putExtra("age", (year - parseInt) + "");
				}
				intent.putExtra("uid", doctor.getUid());

				startActivity(intent);
			}
		});
	}

	@Override
	protected void initView() {
		ViewUtils.inject(this);
	}

	@Override
	protected void initData() {
		Drawable drawable = getResources().getDrawable(R.drawable.state_focus);
		gridView.setSelector(drawable);
		intent = new Intent();
		ProgressDlgUtil.showProgressDlg("加载中...", AskListActivity.this);
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, TVUrl.DoctorList, new RequestCallBack<String>() {

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
						doclist = gson.fromJson(optString, new TypeToken<List<Doctor>>() {
						}.getType());
						myAdapter = new AskAdapter(getApplicationContext(), doclist);
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
