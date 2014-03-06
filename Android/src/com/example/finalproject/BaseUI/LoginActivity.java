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

public class LoginActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		ExitApplication.getInstance().addActivity(this);
		
		Button loginButton = (Button) this.findViewById(R.id.login_button);
		ImageButton regButton_jump = (ImageButton) this.findViewById(R.id.regButton_jump);
		
		//µÇÂ¼ÊÂ¼þ¼àÌý
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, MainActivity.class); 
				startActivity(intent); 
			}
		});
		
		regButton_jump.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class); 
				startActivity(intent); 
			}
		});
		
		CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
		cover_user_photo.setImageResource(R.drawable.head);
	}
}
