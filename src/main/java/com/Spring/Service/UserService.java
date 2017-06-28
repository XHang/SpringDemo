package com.Spring.Service;

import com.Spring.DAO.UserDAO;
import com.Spring.Model.User;

public class UserService {
	private UserDAO dao=null;
	public void add(User u){
		dao.add(u);
	}
	public UserDAO getDao() {
		return dao;
	}
	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

}
