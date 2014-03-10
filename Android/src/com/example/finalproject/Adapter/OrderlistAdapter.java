package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderlistAdapter extends BaseAdapter{
	private List<OrderlistCard> data;
	private Context context;
	private LayoutInflater mInflater;
	
	public OrderlistAdapter(List<OrderlistCard> data, Context context) {
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
			convertView = mInflater.inflate(R.layout.orderitem, null);
			
			holder = new ViewHolder();
			holder.tag = (RelativeLayout) convertView.findViewById(R.id.warnning_tag);
			holder.time = (TextView) convertView.findViewById(R.id.order_list_time);
			holder.price = (TextView) convertView.findViewById(R.id.order_list_cost);

			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		OrderlistCard card = data.get(position);
		if (card.isFinish()) {
			holder.tag.setVisibility(View.INVISIBLE);
		}
		holder.time.setText(card.gettime());
		holder.price.setText("Ïû·Ñ £¤"+card.getPrice());
		
		return convertView;
	}
	
	static class ViewHolder{
		RelativeLayout tag;
		TextView time;
		TextView price;
	}
}
