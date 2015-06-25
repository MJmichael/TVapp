package com.example.tvapp.activity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tvapp.R;
import com.example.tvapp.api.TVUrl;

public class VideoInfoActivity extends BaseActivity {

    private String path = "http://www.114xinqing.com/obar//video//animation//Oktapod.mp4";

    private VideoView mVideoView;

    private ProgressBar progressBar1;

    private TextView tv_progress;

    private final Timer timer = new Timer();
    private TimerTask task;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 要做的事情
            int bufferPercentage = mVideoView.getBufferPercentage();
            tv_progress.setText(bufferPercentage + "%");
            if (bufferPercentage >= 100 | bufferPercentage >= 99) {
                timer.cancel();
                progressBar1.setVisibility(View.GONE);
                tv_progress.setVisibility(View.GONE);
                // 停止计时器
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.play_video_activity);


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {
        path = TVUrl.DefUrl + getIntent().getStringExtra("url");
        path = path.replaceAll(" ", "%20");
        mVideoView = (VideoView) findViewById(R.id.surface_view);
        mVideoView.setVideoPath(path);
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.setSoundEffectsEnabled(true);
        mVideoView.setMediaController(new MediaController(this));

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
    }

    @Override
    protected void initData() {
        // 初始化计时器任务
        task = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        // 启动定时器
        timer.schedule(task, 1000, 1000);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mVideoView != null)
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                // 播放按键
                mVideoView.start();
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                // 播放暂停按键
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                } else {
                    mVideoView.resume();
                }
                break;
            case KeyEvent.KEYCODE_MEDIA_STOP:
                // 停止按键
                mVideoView.stopPlayback();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
