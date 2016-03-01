/**
 * 
 */
package com.lagou.bank.consumer_banking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peker
 * @time  2015年12月11日下午7:57:55
 */
public class RegExp {

	/**
	 * 
	 * @author  Peker
	 * @method  main   
	 * @param   
	 * @return  void
	 */
	public static void main(String[] args) {
		String[] regExp = {
				"\\bhi\\b",
				"\\bhi\\b.*\\bLucy\\b",
				".*\\\\.*",
				""
		};
		String str = "a\\b";
		Pattern pattern = Pattern.compile(regExp[2]);
		Matcher matcher = pattern.matcher(str);
		System.out.println("abc".matches("\\wb\\w"));
		if(matcher.find())
			System.out.println(matcher.group());
		else
			System.out.println("No match found.");
	}

}
