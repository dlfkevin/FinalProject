package com.example.finalproject.Widget;

import com.example.finalproject.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BackTitleBar extends RelativeLayout{
	private TextView tv;
	
	public BackTitleBar(Context context){
		super(context);
	}
	
	public BackTitleBar(Context context, AttributeSet attrs){
		super(context, attrs);

		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.back_titlebar, this);
		tv=(TextView)findViewById(R.id.page_name);
		/**
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});**/
	}
	
	public void setTextResource(String str){
		tv.setText(str);
	}
}
