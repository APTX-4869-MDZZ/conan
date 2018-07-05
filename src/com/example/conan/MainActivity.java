package com.example.conan;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView touchToStart = null;
	ImageView door = null;
	MediaPlayer openMedia = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		touchToStart = (TextView) findViewById(R.id.text_touchToStart);
		door = (ImageView) findViewById(R.id.image_door);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				AlphaAnimation alphAni = new AlphaAnimation((float) 0.2, 1);
				alphAni.setDuration(3000);
				touchToStart.startAnimation(alphAni);
			}
		}).start();
		
		Log.i("MAIN", "oncreate");
		
		door.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("MAIN", "media onclick");

				Log.i("MAIN", "media start");
				openMedia = MediaPlayer.create(MainActivity.this, R.raw.open_door);
				
				openMedia.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.stop();
						mp.release();
					}
				});	
				openMedia.start();
				
				Intent intent = new Intent(MainActivity.this, SelectActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
