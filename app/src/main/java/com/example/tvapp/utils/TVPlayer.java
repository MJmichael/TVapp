package com.example.tvapp.utils;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;

public class TVPlayer implements OnBufferingUpdateListener, OnPreparedListener, OnErrorListener {
	// 播放状态

	public static final int STATE_NULL = 0;// 空闲

	public static final int STATE_BUFFER = 1;// 缓冲

	public static final int STATE_PAUSE = 2;// 暂停

	public static final int STATE_PLAYER = 3;// 播放

	public static final int STATE_PREPARE = 4;// 准备

	public static final int STATE_OVER = 5;// 播放结束

	public static final int STATE_STOP = 6;// 停止

	protected static final String TAG = "TVPlayer";

	private volatile static TVPlayer myPlayer;

	private MediaPlayer mediaPlayer; // 媒体播放器

	// private ProgressBar seekBar; // 拖动条

	private static Timer mTimer = null;; // 计时器

	private TextView tv_player_currentPosition;// 播放进度控件 当前进度
	private TextView tv_player_totalTime;// 总时长

	public TVPlayer(Context context, ProgressBar pb_player_progress) {

		// this.seekBar = pb_player_progress;
		try {
			mediaPlayer = new MediaPlayer(context);
			// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// 设置媒体流类型
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnErrorListener(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public TVPlayer(Context context, TextView tv_player_currentPosition, TextView tv_player_totalTime) {
		this.tv_player_currentPosition = tv_player_currentPosition;
		this.tv_player_totalTime = tv_player_totalTime;
		if(mTimer!=null){
			mTimer.cancel();
			mTimer=null;
		}
		mTimer = new Timer();
		if (mediaPlayer == null) {
			try {
				mediaPlayer = new MediaPlayer(context);
				// mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//
				// 设置媒体流类型
				mediaPlayer.setOnBufferingUpdateListener(this);
				mediaPlayer.setOnPreparedListener(this);
				mediaPlayer.setOnErrorListener(this);
				mediaPlayer.setOnCompletionListener((OnCompletionListener) context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 每一秒触发一次
			mTimer.schedule(timerTask, 0, 1000);
		}
	}

	// 计时器

	TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (mediaPlayer == null)
				return;
			try {
				if (mediaPlayer.isPlaying()) {
					handler.sendEmptyMessage(0); // 发送消息
					LogUtils.d("发送消息");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				if (mediaPlayer != null) {
					// int position = (int) mediaPlayer.getCurrentPosition();
					int duration = (int) mediaPlayer.getDuration();
					int currentDuration = (int) mediaPlayer.getCurrentPosition();
					if (currentDuration <= duration) {
						// 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
						// long pos = seekBar.getMax() * position / duration;
						// seekBar.setProgress((int) pos);
						if (tv_player_currentPosition != null) {
							tv_player_currentPosition.setText(Tools.formatSecondTime(currentDuration));
						}
					}

				}
				break;
			}
		};

	};

	public void play() {
		mediaPlayer.start();
	}

	/**
	 * 
	 * @param url
	 *            url地址
	 */
	public void playUrl(String url) {
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(url); // 设置数据源
			// mediaPlayer.prepare(); // prepare自动播放
			mediaPlayer.prepareAsync(); // prepare自动播放异步
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 暂停

	public void pause() {
		mediaPlayer.pause();
	}

	// 停止

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			timerTask.cancel();
			mediaPlayer = null;
		}
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
		int duration = (int) mp.getDuration();
		tv_player_totalTime.setText("/" + Tools.formatSecondTime(duration));

	}

	/**
	 * 缓冲更新
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {

		// seekBar.setSecondaryProgress(percent);
		// int currentProgress = (int) (seekBar.getMax() *
		// mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration());
		// Log.e(currentProgress + "% play", percent + " buffer");
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		if (extra == -1004) {
			// MyToast.showS(MainActivity.this.getApplicationContext(), text)

			// UIUtils.showToastSafe("抱歉播放器不支持此类型格式。播放出错");

		} else {
			// UIUtils.showToastSafe("抱歉播放出错");

		}
		return false;
	}

	public long getDuration() {
		if (mediaPlayer != null)
			return mediaPlayer.getDuration();
		return 0;
	}

}
