package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class shopCardAdapter extends BaseAdapter {
	private List<ShopCard> data;
	private Context context;
	private LayoutInflater mInflater;
	
	public shopCardAdapter(List<ShopCard> data, Context context) {
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
			convertView = mInflater.inflate(R.layout.shoplist_item, null);
			
			holder = new ViewHolder();
			holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);
			holder.price = (TextView) convertView.findViewById(R.id.price);
			holder.shop_bg = (ImageView) convertView.findViewById(R.id.shop_bg);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ShopCard card = data.get(position);
		holder.shop_name.setText(card.getName());
		holder.price.setText(card.getPrice());
		holder.shop_bg.setImageResource(card.getBg());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView shop_name;
		TextView price;
		ImageView shop_bg;
	}

}
