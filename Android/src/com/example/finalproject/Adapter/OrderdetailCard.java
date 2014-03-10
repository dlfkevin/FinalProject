package com.example.finalproject.Adapter;

public class OrderdetailCard {
	private String itemName;
	private int price;
	private int count;
	
	public OrderdetailCard(String itemName, int price, int count) {
		this.itemName = itemName;
		this.price = price;
		this.count = count;
	}
	
	public String getName(){
		return itemName;
	}
	public String getPrice() {
		return "£¤" + Integer.toString(price);
	}
	public String getCount() {
		return Integer.toString(count) + "·Ý";
	}
}
