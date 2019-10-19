package com.gqs.Util;

//给你7位字符串，获得校验位后拼接返回
public class GetOneUsid {

	public String getOne(String num){
		int mod = 0;
		
		for (int i = 0; i < 7; i++) {
			mod = mod + Integer.parseInt(num.substring(i, i + 1)) * (i + 1);
		}
		mod = mod % 10;
		num = num + mod;
		
		
		
		
		return num;
	}
	
}
