package com.example.tvapp.activity;

import com.example.tvapp.R;
import com.example.tvapp.fragment.ProPsyTest;
import com.example.tvapp.fragment.OtherPsyTest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @类名称: PsyTestActivity
 * @类描述: TODO 心理测试
 * @创建人：liuml
 * @创建时间：2015-6-1 下午1:43:42
 * @备注：
 * @version V1.0
 */
public class PsyTestActivity extends BaseActivity implements OnClickListener {
	private Button button01, button02, button03, button04;
	private FragmentManager fm;
	private FragmentTransaction ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.psy_test_activity);
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initView() {
		button01 = (Button) findViewById(R.id.button01);
		button02 = (Button) findViewById(R.id.button02);

		fm = getFragmentManager();
		ft = fm.beginTransaction();
		/**
		 * 应用进入后，默认选择点击Fragment01
		 */
		ft.replace(R.id.fl_fragment, new ProPsyTest());
		ft.commit();

		button01.setOnClickListener(this);
		button02.setOnClickListener(this);

	}

	@Override
	protected void initData() {

	}

	@Override
	public void onClick(View v) {
		fm = getFragmentManager();
		ft = fm.beginTransaction();
		switch (v.getId()) {

		case R.id.button01:

			ft.replace(R.id.fl_fragment, new ProPsyTest());
			break;

		case R.id.button02:

			ft.replace(R.id.fl_fragment, new OtherPsyTest());
			break;

		default:
			break;
		}
		// 不要忘记提交
		ft.commit();
	}

}
