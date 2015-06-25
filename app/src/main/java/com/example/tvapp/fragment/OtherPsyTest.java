package com.example.tvapp.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.tvapp.R;
import com.example.tvapp.activity.PsyTestInfoActivity;
import com.example.tvapp.adapter.PsyTestAdapter;
import com.example.tvapp.adapter.saMenuItemAdapter;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Exam;
import com.example.tvapp.bean.Info;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class OtherPsyTest extends Fragment {
	View view;
	private String[] proStr = { "性格测试", "职场测试", "情感测试", "社交测试", "情商智商", "成功测试" };
	private GridView gridview;
	private List<Exam> ExamList;
	private PsyTestAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment01, null);
		init();
		initData();
		return view;
	}

	private void init() {
		gridview = (GridView) view.findViewById(R.id.pGridView);

		// 添加Item到网格中
		/* gridview.setAdapter(saMenuItem); */
		ArrayList<Info> mInfos = new ArrayList<Info>();
		ExamList = new ArrayList<Exam>();



		final Intent intent =  new Intent();
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				intent.setClass(getActivity(), PsyTestInfoActivity.class);
				intent.putExtra("class", proStr[arg2]);
				startActivity(intent);
			}
		});

	}
	
	private void initData() {
		ProgressDlgUtil.showProgressDlg("加载中...", getActivity());
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, TVUrl.ExamList1, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				ProgressDlgUtil.stopProgressDlg();
				Throwable throwable = error.getCause(); 
				if (throwable.toString().contains("ConnectException")) {
					MyToast.showS(getActivity().getApplicationContext(), "断网了!");
				} else if (throwable.toString().contains("ConnectTimeoutException")) {
					MyToast.showS(getActivity().getApplicationContext(), "超时了!");
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
						ExamList = gson.fromJson(optString, new TypeToken<List<Exam>>(){}.getType());
						adapter = new PsyTestAdapter(ExamList, getActivity().getApplicationContext());
						gridview.setAdapter(adapter);
						
					} else {
						MyToast.showS(getActivity().getApplicationContext(), "访问数据出错");
						return;
					}
				} catch (JSONException e) {
					MyToast.showS(getActivity().getApplicationContext(), "访问数据出错(异常)");
					e.printStackTrace();
				}
			}

		});

	}

}
