package com.example.tvapp.utils;


import java.io.File;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
/**
 * 工具里类
 * @author  liuml  2014-3-24
 *
 */
@SuppressLint("DefaultLocale")
public class Tools {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"a", "b", "c", "d", "e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String md5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	// 故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
	//
	// 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	//
	// 联通：130、131、132、152、155、156、185、186
	//
	// 电信：133、153、180、189、（1349卫通）
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "-telnum-");
		return m.matches();
	}

	public static boolean validateMobileNumber(String mobileNumber) {
		if (matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", mobileNumber)) {
			return true;
		}
		return false;
	}

	private static boolean matchingText(String expression, String text) {
		Pattern p = Pattern.compile(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		boolean b = m.matches();
		return b;
	}

	// 匹配中国邮政编码：[1-9]\d{5}(?!\d)
	// 评注：中国邮政编码为6位数字
	public static boolean isZipcode(String zipcode) {
		Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");
		Matcher m = p.matcher(zipcode);
		System.out.println(m.matches() + "-zipcode-");
		return m.matches();
	}

	public static boolean isValidEmail(String email) {
		Pattern p = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher m = p.matcher(email);
		System.out.println(m.matches() + "-email-");
		return m.matches();
	}

	// 匹配国内电话号码：d{3}-d{8}|d{4}-d{7}
	// 评注：匹配形式如 0511-4405222 或 021-87888822
	public static boolean isTelfix(String telfix) {
		Pattern p = Pattern.compile("d{3}-d{8}|d{4}-d{7}");
		Matcher m = p.matcher(telfix);
		System.out.println(m.matches() + "-telfix-");
		return m.matches();
	}

	public static boolean isCorrectNickName(String name) {
		int length = name.length();
		if (length >= 1 && length <= 10) {
			return true;
		} else {
			return false;
		}
	}

	// 用户名
	public static boolean isCorrectUserName(String name) {
		Pattern p = Pattern.compile("([A-Za-z0-9]){2,10}");
		Matcher m = p.matcher(name);
		System.out.println(m.matches() + "-name-");
		return m.matches();
	}

	// 密码
	// 验证用户密码:“^[a-zA-Z]w{5,17}$”正确格式为：以字母开头，长度 在6-18之间，只能包含字符、数字和下划线。
	public static boolean isCorrectUserPwd(String pwd) {
		Pattern p = Pattern.compile("\\w{6,18}");
		Matcher m = p.matcher(pwd);
		System.out.println(m.matches() + "-pwd-");
		return m.matches();
	}

	/**
	 * 检查是否存在SDCard
	 * 
	 * @return
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public static String checkContent(String str) {
		if (str == null || str.toLowerCase().equals("null")) {
			return "";
		}
		return str;
	}

	public static String calculationRemainTime(long remainTime) {
		if (remainTime <= 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		// 当前时间
		Calendar currentCal = Calendar.getInstance();
		currentCal.setTimeInMillis(System.currentTimeMillis());
		//
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis() + remainTime * 1000);

		// 年
		int remainYear = cal.get(Calendar.YEAR) - currentCal.get(Calendar.YEAR);
		if (remainYear > 0) {
			sb.append(String.format("%d年", remainYear));
		}
		// 月
		int remainMonth = cal.get(Calendar.MONTH) - currentCal.get(Calendar.MONTH);
		if (remainMonth > 0) {
			sb.append(String.format("%d月", remainMonth));
		}
		// 日
		int remainDay = cal.get(Calendar.DAY_OF_MONTH) - currentCal.get(Calendar.DAY_OF_MONTH);
		if (remainDay > 0) {
			sb.append(String.format("%d日", remainDay));
		}

		if (remainYear <= 0 && remainMonth <= 0 && remainDay <= 0) {
			int remainHour = cal.get(Calendar.HOUR_OF_DAY) - currentCal.get(Calendar.HOUR_OF_DAY);
			int remainMinute = cal.get(Calendar.MINUTE) - currentCal.get(Calendar.MINUTE);
			// int remianSecond = cal.get(Calendar.SECOND) -
			// currentCal.get(Calendar.SECOND);
			// int remainMillisecond = cal.get(Calendar.MILLISECOND) -
			// currentCal.get(Calendar.MILLISECOND);
			// 小时分钟
			// sb.append(String.format("%02d:%02d :%2d.%03d", remainHour,
			// remainMinute, remianSecond, remainMillisecond));
			sb.append(String.format("%02d:%02d", remainHour, remainMinute));
		}

		// sb.append(String.format("%d年%02d月%02d日", cal.get(Calendar.YEAR),
		// cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
		return sb.toString();
	}

	/**
	 * 获取手机Imei号
	 * 
	 * @param context
	 * @return
	 */
	public static String getImeiCode(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public static File getSDDir() {
		/** 获取存储卡路径 */
		File sdcardDir = Environment.getExternalStorageDirectory();
		//路径
		File absoluteFile = sdcardDir.getAbsoluteFile();
		
		/** StatFs 看文件系统空间使用情况 */
		StatFs statFs = new StatFs(sdcardDir.getPath());
		/** Block 的 size */
		Long blockSize = (long) statFs.getBlockSize();
		/** 总 Block 数量 */
		Long totalBlocks = (long) statFs.getBlockCount();
		/** 已使用的 Block 数量 */
		Long availableBlocks = (long) statFs.getAvailableBlocks();
		return sdcardDir;
	}
	
	/**
	 * 格式化毫秒->00:00
	 * */
	public static String formatSecondTime(int millisecond) {
		if (millisecond == 0) {
			return "00:00";
		}
		millisecond = millisecond / 1000;
		int m = millisecond / 60 % 60;
		int s = millisecond % 60;
		return (m > 9 ? m : "0" + m) + ":" + (s > 9 ? s : "0" + s);
	}
	
	
	
	
	
	/**
	 * 判断一个字符串是否为空
	 * @方法名称:isEmpty 
	 * @描述: TODO 
	 * @创建人：liuml 
	 * @创建时间：2015-6-24 上午11:43:43  
	 * @备注：      
	 * @param str
	 * @return    true为空  false为不是
	 * @返回类型：boolean
	 */
	public static boolean isEmpty(String str){
		if(TextUtils.isEmpty(str))
			return true;
		if(str.length()==0)
			return true;
		if("null".equals(str))
			return true;
		return false;
	}
}