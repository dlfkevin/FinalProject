package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.OrderlistAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderdetailAdapter extends BaseAdapter{
	private List<OrderdetailCard> data;
	private Context context;
	private LayoutInflater mInflater;
	
	public OrderdetailAdapter(List<OrderdetailCard> data, Context context){
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
			convertView = mInflater.inflate(R.layout.orderdetail_item, null);
			
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(R.id.detail_name);
			holder.price = (TextView) convertView.findViewById(R.id.detail_price);
			holder.count = (TextView) convertView.findViewById(R.id.detail_count);

			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		OrderdetailCard card = data.get(position);
		holder.itemName.setText(card.getName());
		holder.price.setText(card.getPrice());
		holder.count.setText(card.getCount());
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView itemName;
		TextView price;
		TextView count;
	}
}
