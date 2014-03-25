package com.example.finalproject.Adapter;

import java.util.List;

import com.example.finalproject.R;

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
	private ImageButton minusButton, plusButton;
	private LinearLayout mlLayout;
	
	public ShopMenuAdapter(List<ShopMenuCard> data, Context context){
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
		final ViewHolder holder;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.shop_innerpage_listitem, null);
			
			holder = new ViewHolder();
			holder.itemName = (TextView) convertView.findViewById(R.id.item_shop_name);
			holder.price = (TextView) convertView.findViewById(R.id.single_price_text);
			holder.order = (TextView) convertView.findViewById(R.id.single_day_orders);
			holder.comment = (TextView) convertView.findViewById(R.id.order_comment);
			holder.single_order_amount = (TextView) convertView.findViewById(R.id.order_amount);
			minusButton = (ImageButton) convertView.findViewById(R.id.shop_menu_item_minus);
			plusButton = (ImageButton) convertView.findViewById(R.id.shop_menu_item_plus);
			mlLayout = (LinearLayout) convertView.findViewById(R.id.single_item_amount_layout);
			
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		ShopMenuCard card = data.get(position);
		holder.price_int = card.getSinglePrice();
		holder.itemName.setText(card.getItemname());
		holder.price.setText("��"+Integer.toString(holder.price_int));
		holder.order.setText(card.getSingleDayOrder());
		holder.comment.setText(card.getOrderComment());
		holder.single_order_amount.setText("0");
		
		//��ʼ״̬�����Ӽ��ٺ�������Ӧ�Ŀؼ���������ʾ
		mlLayout.setVisibility(View.INVISIBLE);
		
		//������item���м���
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.count == 0) {
					holder.count = 1;
					holder.single_order_amount.setText(Integer.toString(holder.count));
					mlLayout.setVisibility(View.VISIBLE);
				}
			}
		});
		
		//����item���µ��������ٰ�ť�ļ���
		minusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (holder.count > 0) {
					holder.count -= 1;
					Log.i("test", "-----------"+Integer.toString(holder.count));
					price_sum -= holder.price_int;
					holder.single_order_amount.setText(Integer.toString(holder.count));
					if (holder.count == 0) {
						//minusButton.setVisibility(View.INVISIBLE);
						Log.i("test", "-----------fxxk");
					}
				}
			}
		});
		
		//����item���µ��������Ӱ�ť�ļ���
		plusButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder.count += 1;
				holder.single_order_amount.setText(Integer.toString(holder.count));
				minusButton.setVisibility(View.INVISIBLE);
			}
		});
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView itemName;
		TextView price;
		TextView order;
		TextView comment;
		TextView single_order_amount;
		int count = 0;
		int price_int;
	}
}
