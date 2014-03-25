package com.example.finalproject.BaseUI;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class FindpwActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton PageBack;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpw);
		ExitApplication.getInstance().addActivity(this);
		
		btb = (BackTitleBar) this.findViewById(R.id.findpw_titlebar);
		PageBack = (ImageButton) this.findViewById(R.id.Backbutton);
		button = (Button) this.findViewById(R.id.getcode_button);
		
		btb.setTextResource("找回密码");
		
		//后退按钮监听
		PageBack.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
				
		//获取验证码按钮监听
		button.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FindpwActivity.this, FindpwCodeActivity.class); 
				startActivity(intent);
			}
		});
		
	}
}
