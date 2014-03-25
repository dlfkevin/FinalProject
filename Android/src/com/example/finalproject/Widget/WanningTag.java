package com.example.finalproject.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.finalproject.R;

public class WanningTag extends LinearLayout{
	private TextView tv;
	
	public WanningTag(Context context){
		super(context);
	}
	
	public WanningTag(Context context, AttributeSet attrs){
		super(context, attrs);

		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.wanning_tag, this);
		tv=(TextView)findViewById(R.id.wanning_content);
	}
	
	public void setWanningContent(String str){
		tv.setText(str);
	}
}
