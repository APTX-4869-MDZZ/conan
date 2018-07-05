package com.example.conan.adapter;

import java.util.List;

import com.example.bean.Relationship;
import com.example.conan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RelationshipAdapter extends BaseAdapter{

	List<Relationship> relations = null;
	Context context = null;
	
	public RelationshipAdapter(List<Relationship> relations, Context context) {
		super();
		this.relations = relations;
		this.context = context;
	}

	@Override
	public int getCount() {
		return relations.size();
	}

	@Override
	public Object getItem(int position) {
		return relations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.grid_rela_item, null);
		ImageView image = (ImageView) view.findViewById(R.id.imageview_grid_rela_item);
		TextView relation = (TextView) view.findViewById(R.id.textview_grid_rela_item);
		
		Relationship relationship = (Relationship) getItem(position);
		
		int resId = context.getResources().getIdentifier("head_"+relationship.getImage(), "drawable" , context.getPackageName());
		image.setImageResource(resId);
		image.setMaxWidth(60);
		image.setMaxHeight(60);
		relation.setText(relationship.getRelation());
		
		return view;
	}

}
