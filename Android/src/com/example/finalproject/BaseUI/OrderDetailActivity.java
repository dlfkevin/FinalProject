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

public class OrderDetailActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton pageback;
	private ListView mListView;
	private List<OrderdetailCard> data = new ArrayList<OrderdetailCard>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		ExitApplication.getInstance().addActivity(this);
		
		initListView();
		SetListViewHeight.setListViewHeightBasedOnChildren(mListView);
		
		btb = (BackTitleBar) this.findViewById(R.id.orderdetail_titlebar);
		pageback = (ImageButton) this.findViewById(R.id.Backbutton);
		
		btb.setTextResource("¶©µ¥ÏêÇé");
		
		pageback = (ImageButton) this.findViewById(R.id.Backbutton);
		pageback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void initData(){
		OrderdetailCard card;
		String[] detailname = new String[]{"Ïã¹½ÈâÆ¬", "ÓãÏãÇÑ×Ó", "Á¹°è³´¼¦µ°", "´ó´Ð°è¶¹¸¯", "¼«Æ·ºÚÄ¾¶ú"};
		int[] price = new int[]{10, 9, 8, 11, 12};
		int[] count = new int[]{1, 1, 1, 2, 3};
		
		for (int i = 0; i < detailname.length; i++){
			card = new OrderdetailCard(detailname[i], price[i], count[i]);
			data.add(card);
		}
	}
	
	public void initListView(){
		mListView = (ListView) this.findViewById(R.id.orderdetail_list);
		initData();
		OrderdetailAdapter mAdapter = new OrderdetailAdapter(data, this);
		mListView.setAdapter(mAdapter);
	}
}
