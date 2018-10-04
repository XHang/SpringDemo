package com.SpringBoot.Service;

public class CxfRsServiceImpl implements  CxfRsService {

    @Override
    public String sayHello(String name) {
       System.out.println("welcome to JAX_RS!!! your name is"+name);
       return "success";
    }

}
