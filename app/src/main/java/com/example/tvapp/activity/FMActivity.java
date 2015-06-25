package com.example.tvapp.activity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.Vitamio;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.adapter.FMAdapter;
import com.example.tvapp.api.TVUrl;
import com.example.tvapp.bean.Music;
import com.example.tvapp.utils.MyToast;
import com.example.tvapp.utils.ProgressDlgUtil;
import com.example.tvapp.utils.TVPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class FMActivity extends BaseActivity implements OnCompletionListener {

    private ListView lv_fm;
    private FMAdapter Fmadapter;
    private List<Music> list;
    private TVPlayer player;
    private int playposition = -1;// 记录上次点击的是哪个播放

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fm_activity);
        Vitamio.initialize(this);// 必须初始化这个
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        lv_fm = (ListView) findViewById(R.id.lv_fm);
    }

    @Override
    protected void initData() {
        list = new ArrayList<Music>();
        HttpUtils http = new HttpUtils();
        ProgressDlgUtil.showProgressDlg("加载中...", FMActivity.this);
        http.send(HttpMethod.GET, TVUrl.ObarListMusic, new RequestCallBack<String>() {

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
                        String retarr = object.optString("retarr");
                        list = gson.fromJson(retarr, new TypeToken<List<Music>>() {
                        }.getType());

                        DataSet();
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

    protected void DataSet() {

        Fmadapter = new FMAdapter(getApplicationContext(), list);
        lv_fm.setAdapter(Fmadapter);

        lv_fm.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2, long arg3) {
                // 循环更改歌曲播放状态，fasle为未播放，true为播放


                if (playposition != -1 && playposition != arg2) {
                    Music song = list.get(playposition);
                    song.setPlay(true);
                    song.setPlay_state(TVPlayer.STATE_STOP);
                }

                int length = adapter.getCount();
                for (int i = 0; i < length; i++) {
                    list.get(i).setPlay(false);
                }
                Music music = list.get(arg2);
                int play_state = music.getPlay_state();
                switch (play_state) {
                    case TVPlayer.STATE_PLAYER:
//					 当前曲目播放状态，点击时停止播放，更换图标
                        player.pause();
                        music.setPlay(false);
                        music.setPlay_state(TVPlayer.STATE_PAUSE);
                        Fmadapter.notifyDataSetInvalidated();
                        break;
                    case TVPlayer.STATE_PAUSE:
                        // 当前曲目暂停状态，点击时继续播放，更换图标
                        player.play();
                        music.setPlay(true);
                        music.setPlay_state(TVPlayer.STATE_PLAYER);
                        Fmadapter.notifyDataSetInvalidated();
                        break;
                    case TVPlayer.STATE_STOP:
                        // 当前曲目停止状态，点击时重新播放，更换图标
                        playFM(arg2);
                        break;
                }

            }

        });

    }

    @Override
    public void onBackPressed() {
        isPlayerIng();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        isPlayerIng();
        super.onDestroy();

    }

    /**
     * 判断空
     *
     * @return
     */
    private void isPlayerIng() {
        if (player != null) {
            player.stop();
            player = null;
            System.gc();
        }
    }

    // 完成播放时自动下一曲
    public void next() {
        int size = list.size();
        if (playposition + 1 < size) {
            Music music = list.get(playposition);
            music.setPlay(false);
            music.setPlay_state(TVPlayer.STATE_STOP);
            playFM(playposition + 1);
        } else {
            MyToast.showL(FMActivity.this, "已是最后一首");
        }
    }

    public void playFM(int position) {
        isPlayerIng();
        LinearLayout layout = (LinearLayout) lv_fm.getChildAt(position);
        TextView tv_current = (TextView) layout.findViewById(R.id.mp_current);
        TextView tv_total = (TextView) layout.findViewById(R.id.mp_total);
        Music music = list.get(position);
        MyToast.showL(FMActivity.this, "正在加载");
        player = new TVPlayer(FMActivity.this, tv_current, tv_total);
        String str = TVUrl.DefUrl + music.getUrl();
        str = str.replaceAll(" ", "%20");
        player.playUrl(str);
        music.setPlay(true);
        music.setPlay_state(TVPlayer.STATE_PLAYER);
        playposition = position;
        Fmadapter.notifyDataSetInvalidated();
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        next();
    }


}
