package com.example.finalproject.BaseUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.*;
import com.example.finalproject.General.AvatarProcess;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.CircularImage;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnTouchListener{
	public static final int SNAP_VELOCITY = 200;
	private int screenWidth;
	private int screenHeight;
	private int leftEdge;
	private int rightEdge = 0;
	private int menuPadding = 80;
	private View content;
	private View menu;
	private View v_menu;
	private View menu_top;
	private LinearLayout.LayoutParams v_menuParams;
	private RelativeLayout.LayoutParams menuParams;
	private float xDown;
	private float xMove;
	private float xUp;
	private boolean isMenuVisible;
	private VelocityTracker mVelocityTracker;
	private List<ShopCard> data = new ArrayList<ShopCard>();
	private List<LeftMenuCard> menu_data = new ArrayList<LeftMenuCard>();
	private ImageButton menu_but;
	private Button shutdownButton;
	private ListView menuListView;
	private ListView mListView;
	private Intent motherIntent;
	private TextView menu_username;
	private CircularImage menu_avatar;
	private String current_username, current_password, current_avatar;
	private int[] shopID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ExitApplication.getInstance().addActivity(this);
		initValues();
		initContentList();
		initMenuList();
		content.setOnTouchListener(this);
	}

	private void initValues() {
		Display display = getWindowManager().getDefaultDisplay();
		Point screenSize = new Point();
		display.getSize(screenSize);
		screenWidth = screenSize.x;
		screenHeight = screenSize.y;
		menuPadding = (int) (screenWidth * 0.173);
		
		menu = findViewById(R.id.menu);
		content = findViewById(R.id.content);
		v_menu = findViewById(R.id.virtual_menu);
		
		menuParams = (RelativeLayout.LayoutParams) menu.getLayoutParams();
		menuParams.rightMargin = menuPadding;
		v_menuParams = (LinearLayout.LayoutParams) v_menu.getLayoutParams();
		v_menuParams.width = screenWidth - menuPadding;
		leftEdge = -v_menuParams.width;
		v_menuParams.leftMargin = leftEdge;
		content.getLayoutParams().width = screenWidth;
		
		//监听menubutton和退出button
		menu_but = (ImageButton) findViewById(R.id.menu_button);
		shutdownButton = (Button) findViewById(R.id.shutdown);
		menuButtonClick(menu_but);
		shutdownButtonClick(shutdownButton);
		
	}
	
	//主页面初始化
	private void initContentList(){
		mListView = (ListView) findViewById(R.id.mainListView);
		mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.headview, null));
		initDataShoplist();
		shopCardAdapter mAdapter = new shopCardAdapter(data,this);
		mListView.setAdapter(mAdapter);
		int shopAmount = mListView.getCount();
		shopID = new int[shopAmount];
		ShopListListener(mListView);
	}
	
	//侧栏初始化
	private void initMenuList(){
		
		//初始化用户名和头像的信息
		initUserInfo();
		
		//初始化侧栏的ListView
		menuListView = (ListView) findViewById(R.id.menuListView);
		initDataMenuList();
		LeftMenuCardAdapter mAdapter = new LeftMenuCardAdapter(menu_data,this);
		menuListView.setAdapter(mAdapter);
		
		//监听menuListView里的item点击事件
		menuItemListener(menuListView);
	}

	public void menuButtonClick(ImageButton ib){
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isMenuVisible){
					scrollToContent();
				}else {
					scrollToMenu();
				}
			}
		});
	}
	
	public void shutdownButtonClick(Button b){
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExitApplication.getInstance().exit();
			}
		});
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown);
			if (isMenuVisible) {
				v_menuParams.leftMargin = distanceX;
			} else {
				v_menuParams.leftMargin = leftEdge + distanceX;
			}
			if (v_menuParams.leftMargin < leftEdge) {
				v_menuParams.leftMargin = leftEdge;
			} else if (v_menuParams.leftMargin > rightEdge) {
				v_menuParams.leftMargin = rightEdge;
			}
			v_menu.setLayoutParams(v_menuParams);
			break;
		case MotionEvent.ACTION_UP:
			xUp = event.getRawX();
			if (wantToShowMenu()) {
				if (shouldScrollToMenu()) {
					scrollToMenu();
				} else {
					scrollToContent();
				}
			} else if (wantToShowContent()) {
				if (shouldScrollToContent()) {
					scrollToContent();
				} else {
					scrollToMenu();
				}
			}
			recycleVelocityTracker();
			break;
		}
		return true;
	}

	private boolean wantToShowContent() {
		return xUp - xDown < 0 && isMenuVisible;
	}

	private boolean wantToShowMenu() {
		return xUp - xDown > 0 && !isMenuVisible;
	}

	private boolean shouldScrollToMenu() {
		return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private boolean shouldScrollToContent() {
		return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private void scrollToMenu() {
		new ScrollTask().execute(30);
	}

	private void scrollToContent() {
		new ScrollTask().execute(-30);
	}

	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = v_menuParams.leftMargin;
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge) {
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < leftEdge) {
					leftMargin = leftEdge;
					break;
				}
				publishProgress(leftMargin);
				sleep(20);
			}
			if (speed[0] > 0) {
				isMenuVisible = true;
			} else {
				isMenuVisible = false;
			}
			return leftMargin;
		}

		protected void onProgressUpdate(Integer... leftMargin) {
			v_menuParams.leftMargin = leftMargin[0];
			v_menu.setLayoutParams(v_menuParams);
		}

		protected void onPostExecute(Integer leftMargin) {
			v_menuParams.leftMargin = leftMargin;
			v_menu.setLayoutParams(v_menuParams);
		}
	}

	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void initDataShoplist() {
		
		int[] bgId = new int[]{R.drawable.shop_bg_0, R.drawable.shop_bg_1,
				R.drawable.shop_bg_2, R.drawable.shop_bg_3};
		
		for(int i=0;i<15;i++){
			ShopCard card = new ShopCard("商店"+(i+1), "人均"+(i+20), bgId[i%4]);
			data.add(card);
		}
	}
	
	private void initDataMenuList() {
		LeftMenuCard card;
		int[] bgId = new int[]{R.drawable.menu_hot, R.drawable.menu_shop,
				R.drawable.username, R.drawable.menu_setting};
		String[] item_name = new String[]{"最热门", "全部店铺",
				"我的订单", "设置"};
		
		for(int i=0;i<4;i++){
			card = new LeftMenuCard(bgId[i], item_name[i]);
			menu_data.add(card);
		}
	}
	
	@Override//系统后退按钮事件重写
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
			if(isMenuVisible){
				ExitApplication.getInstance().exit();
			}else {
				scrollToMenu();
			}
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}
	
	//侧栏ListView的监听方法实现
	public void menuItemListener(ListView mListView){
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override   
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				if(id<-1) {
			        // 点击的是headerView或者footerView
			        return;
			    }else if (id == 0) {//点击最热门
					scrollToContent();
				}else if (id == 1) {//点击全部店铺
					
				}else if (id == 2) {//点击我的订单
					Intent intent = new Intent(MainActivity.this, myOrderActivity.class); 
					startActivity(intent);
				}else if(id == 3){//点击设置
					Intent intent = new Intent(MainActivity.this, SettingsActivity.class); 
					intent.putExtra("username", current_username);//传用户名过去
					intent.putExtra("password", current_password);//传密码过去
					intent.putExtra("avatar", current_avatar);//传头像过去
					startActivityForResult(intent, 0);//requestCode设置为0
				}
			}
		});
	}
	
	//店铺ListView的监听方法实现
		public void ShopListListener(ListView mListView){
			mListView.setOnItemClickListener(new OnItemClickListener() {
				@Override   
		        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
					Intent intent = new Intent(MainActivity.this, ShopDetailActivity.class);
					startActivity(intent);
				}
			});
		}

	//Activity回调的重写
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0) {//从SettingsActivity回来
			Bundle newuserinfo = data.getExtras();
			current_username = newuserinfo.getString("username");
			current_password = newuserinfo.getString("password");
			current_avatar = newuserinfo.getString("avatar");
			//解码头像
			Bitmap newAvatarBitmap = AvatarProcess.Base64ToBitmap(current_avatar);
			Drawable NewAvatarDrawable = new BitmapDrawable(getResources(), newAvatarBitmap);
			
			//更新修改后的用户名和头像
			menu_username.setText(current_username);
			menu_avatar.setImageDrawable(NewAvatarDrawable);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//初始化menu里的用户名和头像
	public void initUserInfo(){
		
		//用户名初始化
		current_username = "Life";
		menu_username = (TextView) this.findViewById(R.id.menu_username);
		menu_username.setText(current_username);
		
		//头像初始化
		menu_avatar = (CircularImage) findViewById(R.id.menu_avatar);
		menu_avatar.setImageResource(R.drawable.head);
		InputStream is = getResources().openRawResource(R.drawable.head);
		Bitmap avatarBitmap = BitmapFactory.decodeStream(is);
		current_avatar = AvatarProcess.BitmapToBase64(avatarBitmap);
		
		//密码初始化
		current_password = "hello123";
	}
}
