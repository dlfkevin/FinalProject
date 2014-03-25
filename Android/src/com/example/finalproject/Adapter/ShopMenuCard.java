package com.example.finalproject.Adapter;


public class ShopMenuCard {
	private String itemname;
	private int singleprice;
	private String singledayorder;
	private String ordercomment;
	
	public ShopMenuCard(String itemname, int price, String singledayorder, String ordercomment){
		this.itemname = itemname;
		this.singleprice = price;
		this.singledayorder = singledayorder;
		this.ordercomment = ordercomment;
	}
	
	public String getItemname(){
		return this.itemname;
	}
	public int getSinglePrice(){
		return this.singleprice;
	}
	public String getSingleDayOrder(){
		return this.singledayorder;
	}
	public String getOrderComment(){
		return this.ordercomment;
	}
}
