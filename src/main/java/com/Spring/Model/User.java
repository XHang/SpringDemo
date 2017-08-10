package com.Spring.Model;

public class User {
	private String username;
	
	private String password;
	
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
	//User实体类对象的构造器，用于演示构造器注入
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	//没想到构造器注入还需有一个默认的构造器
	public User(){}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	public void destory(){
		System.out.println("用户对象从Spring容器中狗带了，让我们为他默哀三分钟");
	}
	public void init(){
		System.out.println("用户对象出生了，今天是他第一次来到世界上！");
	}
	

}
