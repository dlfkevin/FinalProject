package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.R.id;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;;

public class ShopMenuAdapter extends BaseAdapter{
	private List<ShopMenuCard> data;
	private Context context;
	private LayoutInflater mInflater;
	private int price_sum = 0;
	private TextView priceSum;
	private int[] amount;
	
	public ShopMenuAdapter(List<ShopMenuCard> data, Context context, TextView priceSum, int[] amount){
		this.data = data;
		this.context = context;
		this.priceSum = priceSum;
		this.amount = amount;
		priceSum.setText(Integer.toString(price_sum));
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
		final ViewHolder holder;
		final int current_pos = position;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.shop_innerpage_listitem, null);
			
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(R.id.item_shop_name);
			holder.price = (TextView) convertView.findViewById(R.id.single_price_text);
			holder.order = (TextView) convertView.findViewById(R.id.single_day_orders);
			holder.comment = (TextView) convertView.findViewById(R.id.order_comment);
			holder.single_order_amount = (TextView) convertView.findViewById(R.id.order_amount);
			holder.minusButton = (ImageButton) convertView.findViewById(R.id.shop_menu_item_minus);
			holder.plusButton = (ImageButton) convertView.findViewById(R.id.shop_menu_item_plus);
			holder.mlLayout = (LinearLayout) convertView.findViewById(R.id.single_item_amount_layout);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ShopMenuCard card = data.get(position);
		holder.price_int = card.getSinglePrice();
		holder.itemName.setText(card.getItemname());
		holder.price.setText("￥"+Integer.toString(holder.price_int));
		holder.order.setText(card.getSingleDayOrder());
		holder.comment.setText(card.getOrderComment());
		if (amount[current_pos] == 0) {
			holder.mlLayout.setVisibility(View.INVISIBLE);
		}else {
			holder.mlLayout.setVisibility(View.VISIBLE);
		}
		holder.single_order_amount.setText(Integer.toString(amount[current_pos]));
		
		//对整个item进行监听
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (amount[current_pos] == 0) {
					amount[current_pos] = 1;
					price_sum += holder.price_int;
					holder.single_order_amount.setText(Integer.toString(amount[current_pos]));
					priceSum.setText(Integer.toString(price_sum));
					holder.mlLayout.setVisibility(View.VISIBLE);
				}
			}
		});
		
		//单个item被下单数量减少按钮的监听
		holder.minusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (amount[current_pos] > 0) {
					amount[current_pos] -= 1;
					price_sum -= holder.price_int;
					holder.single_order_amount.setText(Integer.toString(amount[current_pos]));
					priceSum.setText(Integer.toString(price_sum));
					if (amount[current_pos] == 0) {
						holder.mlLayout.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
		
		//单个item被下单数量增加按钮的监听
		holder.plusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				amount[current_pos] += 1;
				price_sum += holder.price_int;
				holder.single_order_amount.setText(Integer.toString(amount[current_pos]));
				priceSum.setText(Integer.toString(price_sum));
			}
		});
		
		return convertView;
	}
	
	class ViewHolder{
		TextView itemName;
		TextView price;
		TextView order;
		TextView comment;
		TextView single_order_amount;
		LinearLayout mlLayout;
		int price_int;
		ImageButton minusButton, plusButton;
	}
}
