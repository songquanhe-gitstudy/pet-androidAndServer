package com.song.petLeague.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.song.petLeague.bean.User;

public class PreUtils {
	
	public static void putString(String key, String value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	
	public static String getString(String key, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
	
	public static void putInt(String key, int value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
	
	public static int getInt(String key, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getInt(key, (Integer) null);
	}
	
	public static void putBoolean(String key, Boolean value, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	
	public static Boolean getBoolean(String key, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getBoolean(key, false);
	}
	
	public static void remove(String key, Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		sp.edit().remove(key).commit();
	}

	public static User getUserInfo(Context context) {
		String id = PreUtils.getString("id", context);
		String username = PreUtils.getString("username", context);
		String password = PreUtils.getString("password", context);
		String headUrl = PreUtils.getString("headUrl", context);
		String headBgUrl = PreUtils.getString("headBgUrl", context);
		String uPhoneNumbe = PreUtils.getString("uPhoneNumbe", context);
		String uSex = PreUtils.getString("uSex", context);
		String uAge = PreUtils.getString("uAge", context);
		String uCollege = PreUtils.getString("uCollege", context);
		String uMajor = PreUtils.getString("uMajor", context);
		String uClass = PreUtils.getString("uClass", context);
		String uStudentNumber = PreUtils.getString("uStudentNumber", context);
		String uCity = PreUtils.getString("uCity", context);
		String uBirthday = PreUtils.getString("uBirthday()", context);
		String uSignature = PreUtils.getString("uSignature", context);
		String uConstellation = PreUtils.getString("uConstellation", context);
		String uEmotion = PreUtils.getString("uEmotion", context);
		String uUsuallyCity = PreUtils.getString("uUsuallyCity", context);
		String uHabbies = PreUtils.getString("uHabbies", context);
		String uLikeSomething = PreUtils.getString("uLikeSomething", context);
		User user = new User(id, username, password, headUrl, headBgUrl, uPhoneNumbe,
				uSex, uAge, uCollege, uMajor, uClass, uStudentNumber, uCity, uBirthday,
				uSignature, uConstellation, uEmotion, uUsuallyCity, uHabbies, uLikeSomething);
		return user;
	}
	
}














