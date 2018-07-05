package com.example.conan;

import org.json.JSONException;

import com.example.bean.Person;
import com.example.bean.Relationship;
import com.example.conan.adapter.JSONUtil;
import com.example.conan.adapter.RelationshipAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	Person person = null;
	ImageView characterImage = null;
	GridView relationGrid = null;
	TextView descriptionText = null;
	TextView nameText = null;
	ScrollView descSrollView = null;
	Button descButton = null;
	Button relaButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		Intent intent = getIntent();
		person = (Person) intent.getSerializableExtra("person");
		
		getUI();
		setCharacter();
		addListener();
	}
	
	private void setCharacter() {
		int resId = getResources().getIdentifier(person.getBigImage(), "drawable" , getPackageName());
		characterImage.setImageResource(resId);
		
		descriptionText.setText("\u3000\u3000"+person.getDescription());
		nameText.setText(person.getName());
		RelationshipAdapter adapter = new RelationshipAdapter(person.getRelationship(), this);
		relationGrid.setAdapter(adapter);
	}

	private void addListener() {
		descButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				relationGrid.setVisibility(View.INVISIBLE);
				descSrollView.setVisibility(View.VISIBLE);	
				descButton.setTextColor(Color.parseColor("#FFFFFF"));
				relaButton.setTextColor(Color.parseColor("#999999"));
			}
		});
		relaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				relationGrid.setVisibility(View.VISIBLE);
				descSrollView.setVisibility(View.INVISIBLE);
				descButton.setTextColor(Color.parseColor("#999999"));
				relaButton.setTextColor(Color.parseColor("#FFFFFF"));
			}
		});
		relationGrid.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Relationship relationship = person.getRelationship().get(position);
				person = new Person(relationship.getImage(), "head_"+relationship.getImage(), relationship.getImage()+".json");
				try {
					person = JSONUtil.getPerson(person);
					setCharacter();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private void getUI() {
		characterImage = (ImageView) findViewById(R.id.imageView_character);
		descriptionText = (TextView) findViewById(R.id.textview_desc);
		relationGrid = (GridView) findViewById(R.id.gridview_rela);
		nameText = (TextView) findViewById(R.id.textView_name);
		descSrollView = (ScrollView) findViewById(R.id.scrollview_desc);
		descButton = (Button) findViewById(R.id.button_desc);
		relaButton = (Button) findViewById(R.id.button_rela);
	}
	
}
