package com.example.finalproject.BaseUI;

import java.util.ArrayList;
import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.OrderdetailAdapter;
import com.example.finalproject.Adapter.OrderdetailCard;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.General.SetListViewHeight;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class OrderConfirmActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton pageback;
	private ListView mListView;
	private List<OrderdetailCard> data = new ArrayList<OrderdetailCard>();
	private Button codeScanButton;
	private TextView seatInfoTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirm);
		ExitApplication.getInstance().addActivity(this);
		
		initListView();
		SetListViewHeight.setListViewHeightBasedOnChildren(mListView);
		
		TitleBarInit();
		getSeatInfoListener();
	}
	
	public void initData(){
		OrderdetailCard card;
		String[] detailname = new String[]{"香菇肉片", "鱼香茄子", "凉拌炒鸡蛋", "大葱拌豆腐", "极品黑木耳"};
		int[] price = new int[]{10, 9, 8, 11, 12};
		int[] count = new int[]{1, 1, 1, 2, 3};
		
		for (int i = 0; i < detailname.length; i++){
			card = new OrderdetailCard(detailname[i], price[i], count[i]);
			data.add(card);
		}
	}
	
	//具体订单的ListView初始化
	public void initListView(){
		mListView = (ListView) this.findViewById(R.id.current_order_list);
		initData();
		OrderdetailAdapter mAdapter = new OrderdetailAdapter(data, this);
		mListView.setAdapter(mAdapter);
	}
	
	//TitleBar初始化
	public void TitleBarInit(){
		btb = (BackTitleBar) this.findViewById(R.id.order_confirm_titlebar);
		pageback = (ImageButton) this.findViewById(R.id.Backbutton);
		
		btb.setTextResource("订单确认");
		
		pageback = (ImageButton) this.findViewById(R.id.Backbutton);
		pageback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	//座位二维码扫描监听方法
	public void getSeatInfoListener(){
		codeScanButton = (Button) this.findViewById(R.id.code_scan_button);
		seatInfoTextView = (TextView) this.findViewById(R.id.seat_info_gotten);
		codeScanButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
