package com.example.finalproject.BaseUI;

import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_username);
		ExitApplication.getInstance().addActivity(this);
		
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("�޸��û���");
		
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		setname_submit = (Button) this.findViewById(R.id.setname_submit);
		tv = (EditText) this.findViewById(R.id.reset_username);
		
		//����EditText�Ĺ��λ��
		CharSequence text = tv.getText();
		 //Debug.asserts(text instanceof Spannable);
		 if (text instanceof Spannable) {
			 Spannable spanText = (Spannable)text;
		     Selection.setSelection(spanText, text.length());
		 }
		
		//���˼�����
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//submit������
		setname_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�޸������ύ�Ĵ���...
				newname = tv.getText().toString();
				finish();
			}
		});
	}
}
