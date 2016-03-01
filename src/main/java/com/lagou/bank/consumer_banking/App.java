package com.lagou.bank.consumer_banking;

import com.lagou.bank.common.ParseXML;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {

	}
	public static void testParseXMLMethod() {
		System.out.println("Hello World!");
		String text = "<xml>" + "<A>A</A>" + "<B>B</B>" + "<C>亮亮</C>" + "<D>1</D>" + "<E>1</E>" + "<F>165074</F>"
				+ "<G>贫穷</G>" + "<H>1698.0</H>" + "<I>初级士官</I>" + "<J>湖南</J>" + "<K>常德</K>" + "<L>1</L>" + "</xml>";
		long begin = System.currentTimeMillis();
		ParseXML.parseWithJDOM(text);
		long after = System.currentTimeMillis();
		System.out.println("DOM用时" + (after - begin) + "毫秒");		
	}
}