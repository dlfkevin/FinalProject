package com.example.finalproject.BaseUI;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FindpwResetActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton PageBack;
	private Button button;
	private EditText firstpw, secondpw;
	private String fpw, spw;
	private LinearLayout warnningPass, warnningFault;
	private boolean isMatch = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpw_resetpw);
		ExitApplication.getInstance().addActivity(this);
		
		btb = (BackTitleBar) this.findViewById(R.id.findpw_titlebar);
		PageBack = (ImageButton) this.findViewById(R.id.Backbutton);
		button = (Button) this.findViewById(R.id.findpw_finish);
		firstpw = (EditText) this.findViewById(R.id.findpw_firstpw);
		secondpw = (EditText) this.findViewById(R.id.findpw_secondpw);
		warnningPass = (LinearLayout) this.findViewById(R.id.findpw_warnning_pass);
		warnningFault = (LinearLayout) this.findViewById(R.id.findpw_warnning_fault);
		
		btb.setTextResource("找回密码");
		
		//后退按钮监听
		PageBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
				
		//完成按钮监听
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fpw = firstpw.getText().toString();
				spw = secondpw.getText().toString();
				
				if (fpw.equals(spw)) {
					Dialog finishButton = new AlertDialog.Builder(FindpwResetActivity.this)
					.setTitle("重置密码")
					.setMessage("恭喜你！重置密码成功")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(FindpwResetActivity.this, LoginActivity.class); 
							FindpwResetActivity.this.finish();
							startActivity(intent);
						}
					})
					.create();
					finishButton.show();
				}else {
					warnningPass.setVisibility(View.INVISIBLE);
					warnningFault.setVisibility(View.VISIBLE);
				}
			}
		});
	}
}
