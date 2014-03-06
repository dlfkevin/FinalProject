package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftMenuCardAdapter extends BaseAdapter{
	private List<LeftMenuCard> data;
	private Context context;
	private LayoutInflater mInflater;
	
	public LeftMenuCardAdapter(List<LeftMenuCard> data, Context context) {
		this.data = data;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.menulist_item, null);
			
			holder = new ViewHolder();
			holder.item_bg = (ImageView) convertView.findViewById(R.id.menu_icon);
			holder.item = (TextView) convertView.findViewById(R.id.item_name);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		LeftMenuCard card = data.get(position);
		holder.item_bg.setImageResource(card.getBg());
		holder.item.setText(card.getName());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView item;
		ImageView item_bg;
	}
}
