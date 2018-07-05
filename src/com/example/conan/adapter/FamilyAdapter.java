package com.example.conan.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Family;
import com.example.bean.Person;
import com.example.conan.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FamilyAdapter extends BaseAdapter {

	List<Family> families = null;
	Context context = null;
	
	public FamilyAdapter(List<Family> families, Context context) {
		super();
		this.families = families;
		this.context = context;
	}

	@Override
	public int getCount() {
		return families.size();
	}

	@Override
	public Object getItem(int position) {
		return families.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.grid_rela_item, null);
		ImageView image = (ImageView) view.findViewById(R.id.imageview_grid_rela_item);
		TextView familyName = (TextView) view.findViewById(R.id.textview_grid_rela_item);
		
		Family family = (Family) getItem(position);
		int resId = context.getResources().getIdentifier(family.getImage(), "drawable" , context.getPackageName());
		image.setImageResource(resId);
		familyName.setText(family.getName());
		return view;
	}

}
