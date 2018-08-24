package com.springdata.controller;

import com.springdata.bean.User;
import com.springdata.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("simple")
public class SimpleController {

    @Autowired
    private UserDao dao;

    @RequestMapping("getuser")
    @ResponseBody
    public User getUserList(Integer id){
        User user  =  dao.findById(id).get();
        return user;
    }
}
