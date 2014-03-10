package com.example.finalproject.BaseUI;

import java.util.ArrayList;
import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.OrderlistAdapter;
import com.example.finalproject.Adapter.OrderlistCard;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.General.TimeStamp;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class myOrderActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton pageback;
	private List<OrderlistCard> data = new ArrayList<OrderlistCard>();
	private ListView mListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		ExitApplication.getInstance().addActivity(this);
		
		initListView();
		ListViewListener(mListView);
		
		btb = (BackTitleBar) this.findViewById(R.id.myorder_titlebar);
		btb.setTextResource("我的订单");
		
		pageback = (ImageButton) this.findViewById(R.id.Backbutton);
		pageback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void initListView(){
		mListView = (ListView) this.findViewById(R.id.order_ListView);
		initData();
		OrderlistAdapter mAdapter = new OrderlistAdapter(data, this);
		mListView.setAdapter(mAdapter);
	}
	
	public void initData(){
		OrderlistCard card;
		Boolean finish_tag = false;
		String ts;//时间戳
		String price;
		
		for(int i = 1; i < 8; i++){
			ts = TimeStamp.getTimestamp(TimeStamp.getCurrentTime());
			price = Integer.toString(i*11);
			card = new OrderlistCard(finish_tag, ts, price);
			data.add(card);
			finish_tag = true;
		}
	}
	
	public void ListViewListener(ListView mListView){
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override   
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				Intent intent = new Intent(myOrderActivity.this, OrderDetailActivity.class);
				startActivity(intent);
			}
		});
	}
}
