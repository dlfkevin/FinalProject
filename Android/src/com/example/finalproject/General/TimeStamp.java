package com.example.finalproject.General;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {
	
	public static String getCurrentTime(){
		String curTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
		Date ct = new Date(System.currentTimeMillis());
		curTime = sdf.format(ct);
		return curTime;
	}
	
	//���ַ���תΪʱ���
	public static String getTimestamp(String TimeStr){
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
		Date d;
		try{
			d = sdf.parse(TimeStr);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		}catch(ParseException e){
			e.printStackTrace();
		}
		return re_time;
	}
	
	//��ʱ���תΪ�ַ���
	public static String getStrTime(String timestamp){
		String re_Strtime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");
		long lcc_time = Long.valueOf(timestamp);
		re_Strtime = sdf.format(new Date(lcc_time*1000L));
		return re_Strtime;
	}
}
