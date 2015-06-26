package com.example.tvapp.activity;

import com.example.tvapp.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;

import android.app.Activity;
import android.os.Bundle;
@ContentView(R.layout.text_activity)
public class TextActivity extends BaseActivity {
	

	@Override
	protected void initListener() {

	}

	@Override
	protected void initView() {
		ViewUtils.inject(this);
	}

	@Override
	protected void initData() {

	}

}
