package com.example.finalproject.Widget;


import com.example.finalproject.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class textImageButton extends LinearLayout{
	
	private ImageView image;
	private TextView text;

	public textImageButton(Context context, AttributeSet attrs) {
		super(context,attrs);
		
		image = new ImageView(context, attrs);
		image.setPadding(28, 0, 0, 0);
		text = new TextView(context, attrs);
		text.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
		text.setPadding(28, 0, 0, 0);
		setClickable(true);
		setFocusable(true);
		setBackgroundResource(R.color.button_unsel);
		setOrientation(LinearLayout.HORIZONTAL);
		addView(image);
		addView(text);
	}
}
