package com.example.finalproject.BaseUI;

import java.security.PublicKey;

import com.example.finalproject.R;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.BackTitleBar;
import com.example.finalproject.Widget.WanningTag;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class setpwActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton ib;
	private Button setpw_submit;
	private EditText ed1;//������edittext
	private EditText ed2;//������edittext
	private EditText ed3;//����������edittext
	private String oldpw;
	private String newpw1, newpw2;
	private Intent motherIntent;
	private WanningTag wt;
	private int mHeight;
	private LinearLayout mainbody;
	private RelativeLayout.LayoutParams mParams;
	private boolean oldPWcorret = false;
	private boolean newPWcorret = true;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpw);
		ExitApplication.getInstance().addActivity(this);
		motherIntent = getIntent();
		Bundle intentBundle = motherIntent.getExtras();
		oldpw = intentBundle.getString("password");//����û��ĵ�ǰ����

		wt = (WanningTag) this.findViewById(R.id.wanning_layout);
		mainbody = (LinearLayout) this.findViewById(R.id.setpw_mainbody);
		mParams = (RelativeLayout.LayoutParams) mainbody.getLayoutParams();
		getWanningTagHeight();
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("�޸�����");
		
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		setpw_submit = (Button) this.findViewById(R.id.setpw_submit);
		ed1 = (EditText) this.findViewById(R.id.oldpw);
		ed2 = (EditText) this.findViewById(R.id.newpw);
		ed3 = (EditText) this.findViewById(R.id.newpw_sec);
		
		//ԭ����edittext�Ľ������
		ed1.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					//�˴�Ϊ��ý���ʱ�Ĵ�������
				}else {
					//�˴�Ϊʧȥ����ʱ�Ĵ�������
					if (oldpw.equals(ed1.getText().toString())) {
						oldPWcorret = true;
						setWanningTagVisible();
					}else {
						oldPWcorret = false;
						setWanningTagVisible();
					}
				}
			}
		});
		
		//��һ������������edittext�������
		ed2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					//�˴�Ϊ��ý���ʱ�Ĵ�������
					setWanningTagVisible();
				}
			}
		});
		
		//�ڶ�������������edittext�������
		ed3.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					//�˴�Ϊ��ý���ʱ�Ĵ�������
					setWanningTagVisible();
				}else {
					if (ed2.getText().toString().equals(ed3.getText().toString())) {
						newPWcorret = true;
						setWanningTagVisible();
					}else {
						newPWcorret = false;
						setWanningTagVisible();
					}
				}
			}
		});
		
		//TitleBar�ĺ��˼�����
		ib.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				motherIntent.putExtra("password", oldpw);
				setpwActivity.this.setResult(5, motherIntent);
				setpwActivity.this.finish();
			}
		});
				
		//submit��ť����
		setpw_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (ed2.getText().toString().equals(ed3.getText().toString())) {
					newPWcorret = true;
					if (oldPWcorret && newPWcorret) {
						//�¾����붼��ȷ�����ύ
						Dialog submitButton = new AlertDialog.Builder(setpwActivity.this)
						.setTitle("��ʾ")
						.setMessage("��ϲ�㣡�����޸ĳɹ�")
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								newpw1 = ed2.getText().toString();
								motherIntent.putExtra("password", newpw1);
								setpwActivity.this.setResult(5, motherIntent);
								setpwActivity.this.finish();
							}
						})
						.create();
						submitButton.show();
					}else {
						setWanningTagVisible();
					}
				}else {
					newPWcorret = false;
					setWanningTagVisible();
				}
				
				
			}
		});
	}
	
	//��дϵͳ���˼����¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			motherIntent.putExtra("password", oldpw);
			setpwActivity.this.setResult(5, motherIntent);
			setpwActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}
	
	//���ô��󾯸�������ʾ������
	public void setWanningTagVisible(){
		
		if (!oldPWcorret) {
			//ԭ���벻��ȷ
			wt.setWanningContent("ԭ���벻��ȷ��");
			mParams.setMargins(0, mHeight, 0, 0);
			mainbody.setLayoutParams(mParams);
			wt.setVisibility(View.VISIBLE);
			return;
		}else if (!newPWcorret) {
			//�����벻��ȷ
			wt.setWanningContent("�����벻ƥ�䣡");
			mParams.setMargins(0, mHeight, 0, 0);
			mainbody.setLayoutParams(mParams);
			wt.setVisibility(View.VISIBLE);
			return;
		}else {
			//���붼��ȷ��
			mParams.setMargins(0, 0, 0, 0);
			mainbody.setLayoutParams(mParams);
			wt.setVisibility(View.INVISIBLE);
			return;
		}
	}
	
	//��ô��󾯸����ĸ߶�
	public void getWanningTagHeight(){
		int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        wt.measure(w, h);
        mHeight =wt.getMeasuredHeight();
	}
}
