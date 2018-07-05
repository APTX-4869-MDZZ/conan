package com.example.conan;

import java.util.List;

import org.json.JSONException;

import com.example.bean.Family;
import com.example.bean.Person;
import com.example.conan.adapter.FamilyAdapter;
import com.example.conan.adapter.JSONUtil;
import com.example.conan.adapter.PersonAdapter;
import com.example.control.MusicService;
import com.example.control.MusicService.MusicBinder;
import com.example.control.MusicService.OnPlayerStateChangedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectActivity extends Activity implements OnClickListener {

	MusicService mService = null;
	MusicBinder mBinder = null;
	ServiceConnection serviceConn = null;
	boolean binded = false;

	ImageView playImageView = null;
	ImageView pauseImageView = null;
	ImageView nextImageView = null;
	TextView nameTextView = null;
	GridView relationGrid = null;
	
	FamilyAdapter familyAdapter = null;
	PersonAdapter personAdapter = null;
	FamilyOnItemClickListener familyOnItemClickListener = new FamilyOnItemClickListener();;
	PersonOnItemClickListener personOnItemClickListener = new PersonOnItemClickListener();;
	
	List<Family> families;
	List<Person> persons;
	boolean showFamily = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_select);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		getUI();

		Intent intent = new Intent(this, MusicService.class);
		serviceConn = new ServiceConnection() {

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mBinder = (MusicBinder) service;
				mService = mBinder.getMusicService();
				binded = true;

				mService.setOnPlayerStateChangedListener(new OnPlayerStateChangedListener() {

					@Override
					public void onStateChanged(String state) {
						String[] playerState = nameTextView.getText().toString().split("--");
						nameTextView.setText(playerState[0] + "--" + state);
					}

					@Override
					public void onNameChanged(String name) {
						Log.i("listner", name);
						String[] playerState = nameTextView.getText().toString().split("--");
						nameTextView.setText(name + "--" + playerState[1]);
					}
				});
				Log.i("Home", "connected");
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				binded = false;
				Log.i("Home", "disconnected");
			}
		};
		bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
		
		addListener();

	}

	private void getUI() {
		playImageView = (ImageView) findViewById(R.id.image_play);
		pauseImageView = (ImageView) findViewById(R.id.image_pause);
		nextImageView = (ImageView) findViewById(R.id.image_next);
		nameTextView = (TextView) findViewById(R.id.textView_music);
		
		relationGrid = (GridView) findViewById(R.id.gridView_family);
		try {
			JSONUtil.setContext(this);
			families = JSONUtil.getFamily();
			familyAdapter = new FamilyAdapter(families, this);
			relationGrid.setAdapter(familyAdapter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addListener() {
		playImageView.setOnClickListener(this);
		pauseImageView.setOnClickListener(this);
		nextImageView.setOnClickListener(this);
		
		relationGrid.setOnItemClickListener(familyOnItemClickListener);
	}
	
	private class FamilyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			persons = families.get(position).getPersons();
			personAdapter = new PersonAdapter(persons, SelectActivity.this);
			relationGrid.setAdapter(personAdapter);
			showFamily = false;
			relationGrid.setOnItemClickListener(personOnItemClickListener);
		}
		
	}
	
	private class PersonOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Person person = persons.get(position);

			if (person.getBigImage() == null) {
				try {
					person = JSONUtil.getPerson(person);
					persons.set(position, person);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Log.i("select", person.toString());
			Intent intent = new Intent(SelectActivity.this, HomeActivity.class);
			intent.putExtra("person", person);
			startActivity(intent);
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.image_pause:
			mService.pauseMusic();
			pauseImageView.setVisibility(View.GONE);
			playImageView.setVisibility(View.VISIBLE);
			break;
		case R.id.image_play:
			playImageView.setVisibility(View.GONE);
			pauseImageView.setVisibility(View.VISIBLE);
			mService.startMusic();
			break;
		case R.id.image_next:
			mService.nextMusic();
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (binded) {
			unbindService(serviceConn);
			binded = false;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (showFamily) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this);
				builder.setTitle("系统提示");
				builder.setMessage("是否退出应用?");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				builder.setNegativeButton("取消", null);
				Dialog dialog = builder.create();
				dialog.show();
			} else {
				relationGrid.setAdapter(familyAdapter);
				relationGrid.setOnItemClickListener(familyOnItemClickListener);
				showFamily = true;
			}
		}
		return true;
	}
}
