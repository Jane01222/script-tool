package com.xuanwu.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	@org.junit.Test
	public void testRegex(){
		String regex = "create";
		String script = "cREaTECReae proc dgdgnkjkn Create";
		Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(script);
		if(matcher.find()){
			System.out.println(matcher.replaceFirst("ALTER"));
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		boolean x = false; 
		try {
			x = Test.testException();
		} catch (Exception e) {
			System.out.println(x);
		}
		System.out.println(x);
	}
	
	@org.junit.Test
	public void testDate() {
		long time = 1502440570130L;
		Date date = new Date(time);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = format.format(date);
		System.out.println(s);
	}
	
	public static boolean testException() throws Exception {
		if (true) {
			// throw new Exception();
		}
		return true;
	}
}
