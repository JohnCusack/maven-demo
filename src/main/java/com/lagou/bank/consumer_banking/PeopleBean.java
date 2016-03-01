/**
 * 
 */
package com.lagou.bank.consumer_banking;

import java.util.Map;

import com.lagou.bank.common.FooBean;

/**
 * @author Peker
 * @time  2016年1月9日下午1:30:12
 */
public class PeopleBean {
	private FooBean fooBean;
	private int num;
	private Map<String, Integer> map;
	
	
	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public FooBean getFooBean() {
		return fooBean;
	}


	public void setFooBean(FooBean fooBean) {
		this.fooBean = fooBean;
	}


	public Map<String, Integer> getMap() {
		return map;
	}


	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}


	/**
	 * 
	 * @param   
	 * @return  void
	 */
	public static void main(String[] args) {
		PeopleBean peopleBean = new PeopleBean();
		System.out.println(peopleBean.getNum());
		System.out.println(peopleBean.getFooBean());
		System.out.println(peopleBean.getMap());
	}

}
