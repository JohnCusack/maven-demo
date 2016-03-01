/**
 * 
 */
package com.lagou.bank.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Peker
 * @time 2015年12月21日下午1:17:14
 */
public class PropsHelper {
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
//		InputStream inputstream = getClass().getResourceAsStream(filePath);
		InputStream inputstream;
		try {
			inputstream = new BufferedInputStream(new FileInputStream(filePath));
			props.load(inputstream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
	public static void readProperties(String filePath) {
		Map<Object, Object> hashMap = new HashMap<Object, Object>();
		Properties properties = new Properties();
		BufferedInputStream bufferedInputStream;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
			properties.load(bufferedInputStream);
			
			Set<Object> keySet = properties.keySet();
			Iterator<Object> iterator = keySet.iterator();
			// 咦~还可以用for循环，更简单
			while(iterator.hasNext()) {
				String key = (String) iterator.next();
				hashMap.put(key, properties.getProperty(key));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(hashMap);
	}
	public static Map<String, Object> propertiesToMap(String filePath) {
		Properties properties = new Properties();
		BufferedInputStream bufferedInputStream;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
			properties.load(bufferedInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> hashMap = new HashMap<String, Object>((Map) properties);
		System.out.println(properties);
		return hashMap;
	}
	public static void writeProperties(String filePath, String key, String value) {
		 Properties properties = new Properties();
		 try {
			InputStream inputStream = new FileInputStream(filePath);
			properties.load(inputStream);
			for(Object obj:properties.keySet()) {
				if(key.equals(obj)) {
					properties.setProperty(key, value);
				}
				else {
					properties.setProperty((String) obj, properties.getProperty((String) obj));
				}
			}
			OutputStream outputstream = new FileOutputStream(filePath);
			properties.setProperty(key, value);
			properties.store(outputstream, "Update '" + key + "' value");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
