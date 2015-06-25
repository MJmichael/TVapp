package com.example.tvapp.utils;

import android.content.Context;
import android.widget.Toast;

public class MyToast {

	/**
	 * 显示长吐司
	 * 
	 * @param context
	 * @param text
	 */
	public static void showL(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示长吐司
	 * 
	 * @param context
	 * @param text
	 */
	public static void showL(Context context, int text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}

	/**
	 * 显示短吐司
	 * 
	 * @param context
	 * @param text
	 */
	public static void showS(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示短吐司
	 * 
	 * @param context
	 * @param text
	 */
	public static void showS(Context context, int text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
