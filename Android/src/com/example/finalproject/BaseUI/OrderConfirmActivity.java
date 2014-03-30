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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	private Button codeScanButton, cancelButton, submitButton;
	private TextView seatInfoTextView, priceSumTextView;
	private Intent motherIntent;
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confirm);
		ExitApplication.getInstance().addActivity(this);
		motherIntent = getIntent();
		bundle = motherIntent.getExtras();
		widgetInit();//控件的初始化
		initListView();//订单详情的listview初始化
		SetListViewHeight.setListViewHeightBasedOnChildren(mListView);//listview高度的定义
		TitleBarInit();//titlebar初始化
		getSeatInfoListener();//座位信息获取的监听，扫一扫按钮
	}
	
	public void initData(){
		OrderdetailCard card;
		String[] detailname = bundle.getStringArray("order_name");
		int[] price = bundle.getIntArray("single_price");
		int[] count = bundle.getIntArray("amount");
		
		for (int i = 0; i < detailname.length; i++){
			if (count[i] != 0) {
				card = new OrderdetailCard(detailname[i], price[i], count[i]);
				data.add(card);
			}
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
	
	public void widgetInit(){
		cancelButton = (Button) this.findViewById(R.id.order_cancel_button);
		submitButton = (Button) this.findViewById(R.id.order_submit_button);
		priceSumTextView = (TextView) this.findViewById(R.id.order_price_sum);
		String priceSum = bundle.getString("priceSum");
		priceSumTextView.setText("￥" + priceSum);
		
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OrderConfirmActivity.this.finish();
			}
		});
		
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Dialog ad = new AlertDialog.Builder(OrderConfirmActivity.this)
				.setTitle("提示")
				.setMessage("恭喜你！订单提交成功")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(OrderConfirmActivity.this, OrderDetailActivity.class);
						startActivity(intent);
					}
				})
				.create();
				ad.show();
			}
		});
	}
}
