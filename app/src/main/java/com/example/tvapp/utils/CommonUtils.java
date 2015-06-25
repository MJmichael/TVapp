package com.example.tvapp.utils;

public class CommonUtils {
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime < 4000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
