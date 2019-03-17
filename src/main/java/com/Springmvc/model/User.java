package com.Springmvc.model;

/**
 * 实体类对象，用处：
 * 1：作为控制器方法的返回值演示@Response的作用
 * 2：作为对象传入服务器的参数
 * @author Mr-hang
 *
 */
public class User {
	private  String username;
	
	private String password;

	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
