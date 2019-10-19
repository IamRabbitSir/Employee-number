package com.gqs.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GetUsid {

	public String make(String date, List<String> list) {
		// 格式化补0位
		DecimalFormat df = new DecimalFormat("000");
		String max = "001";
		int x = 0;
		// 如果list为null，默认max=001
		// 17110018,17110027
		for (int i = 0; i < list.size(); i++) {
			String a1 = list.get(i);
			int b1 = Integer.parseInt(a1.substring(4, 7));
			if (b1 > x) {
				x = b1;
			}
			;
			max = df.format(x + 1);
			// 最大值是:
			 System.out.println("最大值是:"+x);
			 System.out.println("下一位是:"+(x+1));
		}
		// System.out.println("date值:"+date); 1711
		// 一二三四位 date
		// 五六七位 max
		// 拼接1-7位
		String a2 = date + max;
		// 计算第八位

		int mod = 0;
		for (int j = 0; j < a2.length(); j++) {
			mod = mod + Integer.parseInt(a2.substring(j, j + 1)) * (j + 1);
		}
		mod = mod % 10;
		a2 = a2 + mod;
		// System.out.println("生成的员工编码是:"+a2);
		return a2;
	}

	public List<String> makeAll(int count, String date, List<String> list) {
		// 格式化补0位
		DecimalFormat df = new DecimalFormat("000");
		String max = "001";
		int x = 0;
		// 如果list为null，默认max=001
		// 17110018,17110027
		for (int i = 0; i < list.size(); i++) {
			String a1 = list.get(i);
			int b1 = Integer.parseInt(a1.substring(4, 7));
			if (b1 > x) {
				x = b1;
			}
		}
		List<String> listNum = new ArrayList<String>();
				
		for(int k = 0;k<count;k++){
			max = df.format(x + k + 1);
			String a2 = date + max;
			// 计算第八位
			int mod = 0;
			for (int j = 0; j < a2.length(); j++) {
				mod = mod + Integer.parseInt(a2.substring(j, j + 1)) * (j + 1);
			}
			mod = mod % 10;
			listNum.add(date+max+mod);
			
		}
		// System.out.println("生成的员工编码是:"+a2);

		return listNum;
	}
}
