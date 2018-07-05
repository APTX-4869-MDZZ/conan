package com.example.control;

import java.util.TimerTask;

import com.example.conan.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {

	MediaPlayer bgMusic = null;
	MusicBinder mBinder = null;
	OnPlayerStateChangedListener listener = null;
	int[] musicList = {R.raw.music_1, R.raw.music_2, R.raw.music_3};
	String[] musicName = {"V.A. - 17版本柯南主题旋律同时播放", "ZARD - 運命のルーレット廻して", "倉木麻衣 - Time after time ～花舞う街で～"};
	int currentMusic = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("MS", "oncreate");
		
		mBinder = new MusicBinder();
		try {
			nextMusic();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		bgMusic.stop();
		bgMusic.release();
		bgMusic = null;
		Log.i("MS", "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public void pauseMusic() {
		bgMusic.pause();
		listener.onStateChanged("暂停中");
	}
	
	public void startMusic() {
		bgMusic.start();
		listener.onStateChanged("播放中");
	}
	
	public void setOnPlayerStateChangedListener(OnPlayerStateChangedListener listener) {
		this.listener = listener;
	}
	
	public void nextMusic(){
		currentMusic = (currentMusic + 1)%3;
		if (bgMusic != null) {
			bgMusic.stop();
			bgMusic.release();
		}
		bgMusic = MediaPlayer.create(this, musicList[currentMusic]);
		bgMusic.start();
		bgMusic.setLooping(false);
		bgMusic.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				nextMusic();
			}
		});
		if (listener != null) {
			listener.onNameChanged(musicName[currentMusic]);
			listener.onStateChanged("播放中");
		}
	}
	
	public class MusicBinder extends Binder {
		public MusicService getMusicService() {
			return MusicService.this;
		}
	}

	public interface OnPlayerStateChangedListener {
		public void onNameChanged(String name);
		public void onStateChanged(String state);
	}
	
}
