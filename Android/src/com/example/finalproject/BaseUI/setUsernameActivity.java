package com.example.finalproject.BaseUI;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;

public class setUsernameActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton ib;
	private Button setname_submit;
	private EditText tv;
	private String newname = null;
	private Intent motherIntent;
	private String username;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_username);
		ExitApplication.getInstance().addActivity(this);
		motherIntent = getIntent();
		Bundle userinfo = motherIntent.getExtras();
		username = userinfo.getString("username");
		
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("修改用户名");
		
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		setname_submit = (Button) this.findViewById(R.id.setname_submit);
		tv = (EditText) this.findViewById(R.id.reset_username);
		tv.setText(username);
		
		//设置EditText的光标位置
		CharSequence text = tv.getText();
		 //Debug.asserts(text instanceof Spannable);
		 if (text instanceof Spannable) {
			 Spannable spanText = (Spannable)text;
		     Selection.setSelection(spanText, text.length());
		 }
		
		//后退键监听
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				motherIntent.putExtra("username", username);
				setUsernameActivity.this.setResult(4, motherIntent);
				setUsernameActivity.this.finish();
			}
		});
		
		//submit键监听
		setname_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//修改内容提交的代码...
				username = tv.getText().toString();
				motherIntent.putExtra("username", username);
				setUsernameActivity.this.setResult(4, motherIntent);
				setUsernameActivity.this.finish();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			motherIntent.putExtra("username", username);
			setUsernameActivity.this.setResult(4, motherIntent);
			setUsernameActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}
}
