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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.adapter.ViewGroupExampleAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
@ContentView(R.layout.main)
public class TVMain extends BaseActivity implements OnClickListener {

    // =============================================================================
    // Child views
    // =============================================================================
    @ViewInject(R.id.fancyCoverFlow)
    private FancyCoverFlow fancyCoverFlow;

    private ViewGroupExampleAdapter Myadapter;

    @ViewInject(R.id.bt_ask)
    private Button bt_ask;

    // =============================================================================
    // Supertype overrides
    // =============================================================================


   @Override
    protected void initListener() {


        fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
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



    }

   @Override
    protected void initView() {
        ViewUtils.inject(this);

    }

    @Override
    protected void initData() {
        Myadapter = new ViewGroupExampleAdapter();
        fancyCoverFlow.setAdapter(Myadapter);
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
    }


    // =============================================================================
    // Private classes
    // =============================================================================
    @OnClick(R.id.bt_ask)
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
