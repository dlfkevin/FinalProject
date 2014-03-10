package com.example.finalproject.BaseUI;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.example.finalproject.R;
import com.example.finalproject.Adapter.*;
import com.example.finalproject.General.ExitApplication;
import com.example.finalproject.Widget.CircularImage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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
	
	private void initContentList(){
		mListView = (ListView) findViewById(R.id.mainListView);
		mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.headview, null));
		initDataShoplist();
		shopCardAdapter mAdapter = new shopCardAdapter(data,this);
		mListView.setAdapter(mAdapter);
	}
	
	private void initMenuList(){
		
		CircularImage cover_user_photo = (CircularImage) findViewById(R.id.avatar);
		cover_user_photo.setImageResource(R.drawable.head);
		
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
	
	@Override
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
	
	public void menuItemListener(ListView mListView){
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override   
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
				if(id<-1) {
			        // 点击的是headerView或者footerView
			        return;
			    }else if (id == 0) {
					scrollToContent();
				}else if (id == 2) {
					Intent intent = new Intent(MainActivity.this, myOrderActivity.class); 
					startActivity(intent);
				}else if(id == 3){
					Intent intent = new Intent(MainActivity.this, SettingsActivity.class); 
					startActivity(intent);
				}
			}
		});
	}

}
