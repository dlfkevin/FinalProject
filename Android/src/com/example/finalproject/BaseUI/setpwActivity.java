package com.example.finalproject.BaseUI;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class setpwActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton ib;
	private Button setpw_submit;
	private EditText ed1;//旧密码edittext
	private EditText ed2;//新密码edittext
	private EditText ed3;//二次新密码edittext
	private String oldpw;
	private String newpw;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpw);
		ExitApplication.getInstance().addActivity(this);
		
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("修改密码");
		
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		setpw_submit = (Button) this.findViewById(R.id.setpw_submit);
		ed1 = (EditText) this.findViewById(R.id.oldpw);
		ed2 = (EditText) this.findViewById(R.id.newpw);
		ed3 = (EditText) this.findViewById(R.id.newpw_sec);
		
		//后退键监听
		ib.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
				
		//submit键监听
		setpw_submit.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//修改内容提交的代码...
				oldpw = ed1.getText().toString();
				newpw = ed2.getText().toString();
				finish();
			}
		});		
		
	}
}
