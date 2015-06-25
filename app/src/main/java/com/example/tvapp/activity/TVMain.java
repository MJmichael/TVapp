/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.tvapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.adapter.ViewGroupExampleAdapter;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;

public class TVMain extends BaseActivity implements OnClickListener {

    // =============================================================================
    // Child views
    // =============================================================================

    private FancyCoverFlow fancyCoverFlow;

    private ViewGroupExampleAdapter Myadapter;
    private Button bt_ask;

    private TextView tvTitle;

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


    }

    @Override
    protected void initListener() {
        fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long arg3) {
                int count = adapter.getChildCount();
                ViewGroup group = (ViewGroup) view;
                int childCount = group.getChildCount();
                for (int i = 0; i < count; i++) {

                    adapter.getChildAt(i).setBackgroundDrawable(null);// 将所有的图片先去掉背景
                }
                View childAt = adapter.getChildAt(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }

        });

        bt_ask.setOnClickListener(this);

        fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent;
                switch (arg2) {
                    case 0:

                        intent = new Intent();
                        intent.setClass(TVMain.this, PsyTestActivity.class);
                        startActivity(intent);

                        break;
                    case 1:// 咨询师
                        intent = new Intent();
                        intent.setClass(TVMain.this, AskListActivity.class);
                        startActivity(intent);
                        break;
                    case 2:// 视频
                        intent = new Intent();
                        intent.setClass(TVMain.this, VideoActivity.class);
                        startActivity(intent);
                        break;
                    case 3:// fm

                        intent = new Intent();
                        intent.setClass(TVMain.this, FMActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }

            }
        });
        fancyCoverFlow.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            }
        });

        Drawable drawable = getResources().getDrawable(R.drawable.state_focus);


    }

    @Override
    protected void initView() {
        bt_ask = (Button) findViewById(R.id.bt_ask);
        this.fancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.fancyCoverFlow);
        fancyCoverFlow.setReflectionEnabled(true);
        fancyCoverFlow.setReflectionRatio(0.3f);//反射 比例
        fancyCoverFlow.setReflectionGap(10);//反射间隙

        this.fancyCoverFlow.setUnselectedAlpha(1.0f);// 透明度？
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);// 饱和度
        this.fancyCoverFlow.setUnselectedScale(0.1f);// 未选中规模
        this.fancyCoverFlow.setSpacing(0);// 间隔
        this.fancyCoverFlow.setMaxRotation(1);// 进入旋转度数
        this.fancyCoverFlow.setScaleDownGravity(0.2f);// 下重力
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
        fancyCoverFlow.setSelection(1);// 设置当前位置
        this.fancyCoverFlow.setAdapter(Myadapter);

    }

    @Override
    protected void initData() {
        Myadapter = new ViewGroupExampleAdapter();
    }


    // =============================================================================
    // Private classes
    // =============================================================================

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_ask:
                intent = new Intent(this, TextActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }
}
