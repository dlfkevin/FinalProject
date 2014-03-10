package com.example.finalproject.BaseUI;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;
import android.widget.RelativeLayout;

public class SettingsActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton ib;
	private Button logoutButton;
	private RelativeLayout avatarLayout;//ͷ��������
	private RelativeLayout setnameLayout;//�û���������
	private RelativeLayout setpwLayout;//����������
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		ExitApplication.getInstance().addActivity(this);
		
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("����");
		
		//���˰�ť����
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//��ť����
		logoutButton = (Button)this.findViewById(R.id.logoutButton);
		logoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
				ExitApplication.getInstance().ActivityFinish();
				startActivity(intent);
			}
		});
		
		//����������ļ���
		avatarLayout = (RelativeLayout) this.findViewById(R.id.setavatar_layout);
		setnameLayout = (RelativeLayout) this.findViewById(R.id.setusername_layout);
		setpwLayout = (RelativeLayout) this.findViewById(R.id.setpw_layout);
		
		avatarLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setnameLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setUsernameActivity.class);
				startActivity(intent);
			}
		});
		
		setpwLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setpwActivity.class);
				startActivity(intent);
			}
		});
	}
}
