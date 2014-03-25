package com.example.finalproject.BaseUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.finalproject.R;
import com.example.finalproject.General.AvatarProcess;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.General.TimeStamp;
import com.example.finalproject.Widget.BackTitleBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity{
	private BackTitleBar btb;
	private ImageButton ib;
	private ImageView iv;//ͷ���iv
	private TextView username_tv;//�û�����tv
	private Button logoutButton;
	private RelativeLayout avatarLayout;//ͷ��������
	private RelativeLayout setnameLayout;//�û���������
	private RelativeLayout setpwLayout;//����������
	private String avatarName;
	private String currentTimestamp;
	private String path;
	private File out;
	private Intent motherIntent;
	private String username, password, avatar;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		ExitApplication.getInstance().addActivity(this);
		initUserInfo();
		
		btb = (BackTitleBar) this.findViewById(R.id.settings_titlebar);
		btb.setTextResource("����");
		
		//���˰�ť����
		ib = (ImageButton) this.findViewById(R.id.Backbutton);
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				motherIntent.putExtra("username", username);
				motherIntent.putExtra("password", password);
				motherIntent.putExtra("avatar", avatar);
				SettingsActivity.this.setResult(0, motherIntent);
				SettingsActivity.this.finish();
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
			//ͷ�����ü���
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAvatarSelection();
			}
		});
		
		setnameLayout.setOnClickListener(new OnClickListener() {
			//�û������ü���
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setUsernameActivity.class);
				intent.putExtra("username", username);
				startActivityForResult(intent, 4);
			}
		});
		
		setpwLayout.setOnClickListener(new OnClickListener() {
			//�������ü���
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setpwActivity.class);
				intent.putExtra("password", password);
				startActivityForResult(intent, 5);
			}
		});
	}
	
	//ͷ������Alert
	public void showAvatarSelection(){
		final AlertDialog dig = new AlertDialog.Builder(SettingsActivity.this).create();
		dig.show();
		Window window = dig.getWindow();
		window.setContentView(R.layout.avatar_alert);
		
		//ѡ������
		Button b1 = (Button) window.findViewById(R.id.take_photo);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dig.dismiss();
				
				currentTimestamp = TimeStamp.getTimestamp(TimeStamp.getCurrentTime());//��õ�ǰʱ���ʱ���
				avatarName = currentTimestamp + ".jpg";//�õ�ǰʱ���ʱ���ȥ���������������Ƭ
				
				if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
					//��SD���½�һ����Whatseat���ļ��������������㵽����Ƭ
					File sdPath = Environment.getExternalStorageDirectory();
					path = sdPath.getPath() + "/Whatseat";
					out = new File(path);
					if (!out.exists()) {
					    out.mkdirs();
					}
					out = new File(path, avatarName);
					
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					//ָ������������պ����Ƭ�洢��·��
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
					startActivityForResult(intent, 2);
				}
			}
		});
		
		//ѡ��������ѡ��
		Button b2 = (Button) window.findViewById(R.id.selectFromAlarm);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dig.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);
			}
		});
		
		//�ر�AlertDialog
		Button b3 = (Button) window.findViewById(R.id.CloseAlert);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dig.cancel();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// �����ֱ�Ӵ�����ȡ
		case 1:
			startPhotoZoom(data.getData());
			break;
			
		// ����ǵ����������ʱ
		case 2:
			File temp = new File(path + File.separator + avatarName);
			startPhotoZoom(Uri.fromFile(temp));
			break;
			
		// ȡ�òü����ͼƬ
		case 3:
			if(data != null){
				setPicToView(data);
			}
			break;
			
		// �����û���
		case 4:
			UsernameUpdate(data);
			break;
			
		//��������
		case 5:
			passwordUpdate(data);
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//�ü�ͼƬ����ʵ��
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//crop = true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	
	//����ü�֮���ͼƬ����
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(getResources(), photo);
			
			avatar = AvatarProcess.BitmapToBase64(photo);
			
			iv.setImageDrawable(drawable);
		}
	}
	
	public void initUserInfo(){
		motherIntent = getIntent();
		Bundle userinfo = motherIntent.getExtras();
		username = userinfo.getString("username");
		password = userinfo.getString("password");
		avatar = userinfo.getString("avatar");
		
		//�Դ�MainActivity��������ͷ����н���
		Bitmap dBitmap = AvatarProcess.Base64ToBitmap(avatar);
		Drawable drawable = new BitmapDrawable(getResources(), dBitmap);
		
		//���õ�ǰActivity���ͷ����û���
		iv = (ImageView) this.findViewById(R.id.setting_avatar);
		iv.setImageDrawable(drawable);
		username_tv = (TextView) this.findViewById(R.id.set_item_username);
		username_tv.setText(username);
	}
	
	public void UsernameUpdate(Intent data){
		Bundle bundle = data.getExtras();
		username = bundle.getString("username");
		username_tv.setText(username);
	}
	
	public void passwordUpdate(Intent data){
		Bundle bundle = data.getExtras();
		password = bundle.getString("password");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			motherIntent.putExtra("username", username);
			motherIntent.putExtra("password", password);
			motherIntent.putExtra("avatar", avatar);
			SettingsActivity.this.setResult(0, motherIntent);
			SettingsActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}
}
