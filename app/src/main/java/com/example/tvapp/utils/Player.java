package com.example.tvapp.utils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Player implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnErrorListener {
	// 播放状态

	public static final int STATE_NULL = 0;// 空闲

	public static final int STATE_BUFFER = 1;// 缓冲

	public static final int STATE_PAUSE = 2;// 暂停

	public static final int STATE_PLAYER = 3;// 播放

	public static final int STATE_PREPARE = 4;// 准备

	public static final int STATE_OVER = 5;// 播放结束

	public static final int STATE_STOP = 6;// 停止

	protected static final String TAG = "TVPlayer";
	// private int currentDuration = 0;// 已经播放时长

	public MediaPlayer mediaPlayer; // 媒体播放器

	private ProgressBar seekBar; // 拖动条

	private Timer mTimer = new Timer(); // 计时器

	private TextView tv_player_currentPosition;// 播放进度控件

	// 初始化播放器

	public Player(ProgressBar pb_player_progress) {
		super();
		this.seekBar = pb_player_progress;

		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型

			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnErrorListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 每一秒触发一次

		mTimer.schedule(timerTask, 0, 1000);
	}

	public Player(ProgressBar pb_player_progress, TextView tv_player_currentPosition) {
		super();
		this.seekBar = pb_player_progress;
		this.tv_player_currentPosition = tv_player_currentPosition;
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型

			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnErrorListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 每一秒触发一次

		if (mediaPlayer != null) {

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
				if (mediaPlayer.isPlaying() && seekBar.isPressed() == false) {
					handler.sendEmptyMessage(0); // 发送消息

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (mediaPlayer != null) {

				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();
				int currentDuration = mediaPlayer.getCurrentPosition();
				if (duration > 0) {
					// 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）

					long pos = seekBar.getMax() * position / duration;
					seekBar.setProgress((int) pos);
					if (tv_player_currentPosition != null) {

						tv_player_currentPosition.setText(Tools.formatSecondTime(currentDuration));
					}
				}

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

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
		Log.e("mediaPlayer", "onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.e("mediaPlayer", "onCompletion");
	}

	/**
	 * 缓冲更新
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		seekBar.setSecondaryProgress(percent);
		int currentProgress = seekBar.getMax() * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
		Log.e(currentProgress + "% play", percent + " buffer");
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

}