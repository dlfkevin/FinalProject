package com.example.finalproject.BaseUI;

import java.util.ArrayList;
import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.ShopMenuAdapter;
import com.example.finalproject.Adapter.ShopMenuCard;
import com.example.finalproject.Adapter.shopCardAdapter;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class ShopDetailActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton PageBack;
	private String shopname;
	private ListView mListView;
	private List<ShopMenuCard> data = new ArrayList<ShopMenuCard>();
	private ImageButton submit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_innerpage);
		ExitApplication.getInstance().addActivity(this);
		shopname = "’≈∞ÿ÷• øµ∞∏‚µÍ";
		TitleBarInit(shopname);
		MenuListInit();
		submitButtonListener();
	}
	
	public void MenuListInit(){
		mListView = (ListView) this.findViewById(R.id.shop_innerpage_menulist);
		MenuListDataInit();
		ShopMenuAdapter mAdapter = new ShopMenuAdapter(data, this);
		mListView.setAdapter(mAdapter);
	}
	
	public void MenuListDataInit(){
		ShopMenuCard card;
		for (int i = 0; i < 6; i++) {
			card = new ShopMenuCard("÷• ø√√√√", 20+i, Integer.toString(1024+i), Integer.toString(2048+i));
			data.add(card);
		}
	}
	
	public void TitleBarInit(String shopname){
		btb = (BackTitleBar) this.findViewById(R.id.shop_innerpage_titlebar);
		PageBack = (ImageButton) this.findViewById(R.id.Backbutton);
		btb.setTextResource(shopname);
		
		//◊¢≤·“≥∫ÛÕÀ∞¥≈•º‡Ã˝
		PageBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void setSingleItemWidth(){
		
	}
	
	public void submitButtonListener(){
		submit = (ImageButton) this.findViewById(R.id.shop_innerpage_order_ok);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ShopDetailActivity.this, OrderConfirmActivity.class);
				Log.i("test", "-----------i am here-----------");
				startActivity(intent);
			}
		});
	}
}
