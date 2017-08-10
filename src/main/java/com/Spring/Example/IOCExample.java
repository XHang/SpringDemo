package com.Spring.Example;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.Spring.Model.User;

public class IOCExample {
	private Map<String,String> map;
	private List<Integer> list;
	private Set <String> set;
	private List <User> users;
	
	public void exec(User u) {
		System.out.println("执行一股操作");
		
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(String s:map.keySet()){
			sb.append("map_key="+s+",");
			sb.append("map_value="+map.get(s));
		}
		sb.append("\n");
		for(Integer i:list){
			sb.append("list="+i);
		}
		sb.append("\n");
		for(String s:set){
			sb.append("Set="+s);
		}
		sb.append("\n");
		return sb.toString();
		
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	

}
