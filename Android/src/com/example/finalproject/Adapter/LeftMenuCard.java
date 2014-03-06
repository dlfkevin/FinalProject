package com.example.finalproject.Adapter;

public class LeftMenuCard {
	private String item;
	private int item_bg;
	
	public LeftMenuCard(int item_bg, String item) {
		this.item = item;
		this.item_bg =item_bg;
	}
	
	public String getName() {
		return item;
	}
	public void setName(String item) {
		this.item = item;
	}
	public int getBg() {
		return item_bg;
	}
	public void setBg(int item_bg) {
		this.item_bg = item_bg;
	}
}
