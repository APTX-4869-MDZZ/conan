package com.example.conan.adapter;

import java.util.List;

import com.example.bean.Family;
import com.example.bean.Person;
import com.example.conan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonAdapter extends BaseAdapter{

	List<Person> persons = null;
	Context context = null;
	
	public PersonAdapter(List<Person> persons, Context context) {
		super();
		this.persons = persons;
		this.context = context;
	}

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		return persons.get(position);
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
		
		Person person = (Person) getItem(position);
		int resId = context.getResources().getIdentifier(person.getHeadImage(), "drawable" , context.getPackageName());
		image.setImageResource(resId);
		familyName.setText(person.getName());
		return view;
	}

}
