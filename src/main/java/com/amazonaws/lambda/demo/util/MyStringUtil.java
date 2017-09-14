package com.amazonaws.lambda.demo.util;

import java.util.ArrayList;
import java.util.List;

public class MyStringUtil {

	/**
	 * 驼峰变下划线
	 * 
	 * @param str
	 *            驼峰命名字符串
	 * @return 下划线命名字符串
	 */
	public static String camelToUnderLine(String str) {
		if (str != null || !str.trim().equals("")) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (Character.isUpperCase(c)) {
					builder.append("_");
					builder.append(Character.toLowerCase(c));
				} else {
					builder.append(c);
				}
			}
			return builder.toString();
		}
		return str;
	}

	public static List<Integer> sIdsToList(String str) throws Exception {
		List<Integer> list = new ArrayList<Integer>();
		if (str != null && !str.trim().equals("")) {
			try {
				String s[] = str.split(",");
				for (int i = 0; i < s.length; i++) {
					list.add(Integer.parseInt(s[i]));
				}
				return list;
			} catch (Exception e) {
				throw e;
			}
		} else
			return null;
	}
}
