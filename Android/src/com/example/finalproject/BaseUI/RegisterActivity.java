package com.example.finalproject.BaseUI;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.CircularImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		ExitApplication.getInstance().addActivity(this);
		
		ImageButton regPageBack = (ImageButton) this.findViewById(R.id.regPageBack);
		Button reg_button = (Button) this.findViewById(R.id.reg_button);
		
		//×¢²áÒ³ºóÍË°´Å¥¼àÌý
		regPageBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//×¢²á°´Å¥¼àÌý
		reg_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this, MainActivity.class); 
				startActivity(intent);
			}
		});
	}
}
