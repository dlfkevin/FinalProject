package com.example.finalproject.Adapter;

import android.R.integer;
import android.widget.ImageView;

public class ShopCard {
	private String name;
	private String price;
	private int shop_bg;
	
	public ShopCard(String name, String price, int shop_bg) {
		this.name = name;
		this.price = price;
		this.shop_bg =shop_bg;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getBg() {
		return shop_bg;
	}
	public void setBg(int shop_bg) {
		this.shop_bg = shop_bg;
	}
}
