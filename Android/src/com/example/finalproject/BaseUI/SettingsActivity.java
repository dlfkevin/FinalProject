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
	private ImageView iv;//头像的iv
	private TextView username_tv;//用户名的tv
	private Button logoutButton;
	private RelativeLayout avatarLayout;//头像设置项
	private RelativeLayout setnameLayout;//用户名设置项
	private RelativeLayout setpwLayout;//密码设置项
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
		btb.setTextResource("设置");
		
		//后退按钮监听
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
		
		//按钮监听
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
		
		//三个设置项的监听
		avatarLayout = (RelativeLayout) this.findViewById(R.id.setavatar_layout);
		setnameLayout = (RelativeLayout) this.findViewById(R.id.setusername_layout);
		setpwLayout = (RelativeLayout) this.findViewById(R.id.setpw_layout);
		
		avatarLayout.setOnClickListener(new OnClickListener() {
			//头像设置监听
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAvatarSelection();
			}
		});
		
		setnameLayout.setOnClickListener(new OnClickListener() {
			//用户名设置监听
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setUsernameActivity.class);
				intent.putExtra("username", username);
				startActivityForResult(intent, 4);
			}
		});
		
		setpwLayout.setOnClickListener(new OnClickListener() {
			//密码设置监听
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingsActivity.this, setpwActivity.class);
				intent.putExtra("password", password);
				startActivityForResult(intent, 5);
			}
		});
	}
	
	//头像设置Alert
	public void showAvatarSelection(){
		final AlertDialog dig = new AlertDialog.Builder(SettingsActivity.this).create();
		dig.show();
		Window window = dig.getWindow();
		window.setContentView(R.layout.avatar_alert);
		
		//选择拍照
		Button b1 = (Button) window.findViewById(R.id.take_photo);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dig.dismiss();
				
				currentTimestamp = TimeStamp.getTimestamp(TimeStamp.getCurrentTime());//获得当前时间的时间戳
				avatarName = currentTimestamp + ".jpg";//用当前时间的时间戳去命名即将拍摄的照片
				
				if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
					//在SD卡新建一个叫Whatseat的文件夹来保存所拍摄到的照片
					File sdPath = Environment.getExternalStorageDirectory();
					path = sdPath.getPath() + "/Whatseat";
					out = new File(path);
					if (!out.exists()) {
					    out.mkdirs();
					}
					out = new File(path, avatarName);
					
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					//指定调用相机拍照后的照片存储的路径
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
					startActivityForResult(intent, 2);
				}
			}
		});
		
		//选择从相册中选择
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
		
		//关闭AlertDialog
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
		// 如果是直接从相册获取
		case 1:
			startPhotoZoom(data.getData());
			break;
			
		// 如果是调用相机拍照时
		case 2:
			File temp = new File(path + File.separator + avatarName);
			startPhotoZoom(Uri.fromFile(temp));
			break;
			
		// 取得裁剪后的图片
		case 3:
			if(data != null){
				setPicToView(data);
			}
			break;
			
		// 更新用户名
		case 4:
			UsernameUpdate(data);
			break;
			
		//更新密码
		case 5:
			passwordUpdate(data);
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//裁剪图片方法实现
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	
	//保存裁剪之后的图片数据
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
		
		//对从MainActivity传过来的头像进行解码
		Bitmap dBitmap = AvatarProcess.Base64ToBitmap(avatar);
		Drawable drawable = new BitmapDrawable(getResources(), dBitmap);
		
		//设置当前Activity里的头像和用户名
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
