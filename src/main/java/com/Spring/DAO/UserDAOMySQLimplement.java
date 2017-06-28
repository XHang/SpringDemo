package com.Spring.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.Spring.Model.User;

public class UserDAOMySQLimplement implements UserDAO {
	//定义一个DataSource,使用自动注入来初始化。
	private DataSource datasource;			
	
	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}
	//往数据库保存一条记录
	public void add(User u) {
		try {
			Connection conn=datasource.getConnection();
			Statement stmt=conn.createStatement();
			stmt.executeUpdate("insert into User value(null,'12345','张三')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("成功存入数据库，得到经验100exp");
	}


}
