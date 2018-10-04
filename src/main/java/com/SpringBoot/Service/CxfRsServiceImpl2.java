package com.SpringBoot.Service;

import javax.ws.rs.Path;

@Path("/sayHello2")
public class CxfRsServiceImpl2 implements  CxfRsService {

    @Override
    //覆写接口的path，实现自己的path
    public String sayHello(String name) {
       System.out.println("welcome to JAX_RS2!!! your name is"+name);
       return "success";
    }

}
