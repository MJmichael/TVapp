package com.example.tvapp.activity;

import com.example.tvapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GuideActivity extends BaseActivity{

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000*3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent intent=new Intent(GuideActivity.this,TVMain.class);
				startActivity(intent);
				GuideActivity.this.finish();
				
				
			}
		}).start();
		

	
	
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initView() {
		setContentView(R.layout.guide_activity);
	}

	@Override
	protected void initData() {

	}
}
