/**
 * 
 */
package com.lagou.bank.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Peker
 * @time  2015年12月21日下午2:43:05
 */
public class PropsHelperTest {
	String filePath;
	/**  
	 * @author  Peker
	 * @title   setUp   
	 * @param   
	 * @return  void   
	 */
	@Before
	public void setUp() throws Exception {
		filePath = "target/classes/jedis.properties";
	}

	/**  
	 * @author  Peker
	 * @title   tearDown   
	 * @param   
	 * @return  void   
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.lagou.bank.common.PropsHelper#readValue(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReadValue() {
		String filePath = "target/classes/jedis.properties";
		System.out.println("---------------由key获取value, 这个filePath真蛋疼----------------");
		String value = PropsHelper.readValue(filePath, "redis.pool.maxActive");
		System.out.println(value);
	}

	/**
	 * Test method for {@link com.lagou.bank.common.PropsHelper#readProperties(java.lang.String)}.
	 */
	@Test
	public void testReadProperties() {

		System.out.println("-----------迭代出来 再 put 到 map 中去-------------");
		PropsHelper.readProperties(filePath);

		System.out.println("---------------强制转换-----------");
		PropsHelper.propertiesToMap(filePath);
		
		System.out.println("-------------------试一试写操作---------------------");
		PropsHelper.writeProperties(filePath, "NEWKEY", "NEWVALUE");
		System.out.println("-------------------看看有没有---------------------");
		PropsHelper.readProperties(filePath);

	}
}
