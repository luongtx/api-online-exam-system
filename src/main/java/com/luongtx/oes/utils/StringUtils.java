package com.luongtx.oes.utils;

public class StringUtils {
	public static String toSearchString(String str) {
		if (str == null) {
			return "%%";
		}
		return "%" + str + "%";
	}
}
