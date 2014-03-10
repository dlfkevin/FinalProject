package com.example.finalproject.Adapter;

import com.example.finalproject.General.TimeStamp;

public class OrderlistCard {
	private Boolean finish_tag;
	private String timestamp;
	private String price;
	
	public OrderlistCard(Boolean finish_tag, String timestamp, String price) {
		this.finish_tag = finish_tag;
		this.timestamp = timestamp;
		this.price = price;
	}
	
	public Boolean isFinish(){
		return finish_tag;
	}
	public String gettime() {
		return TimeStamp.getStrTime(timestamp);
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
