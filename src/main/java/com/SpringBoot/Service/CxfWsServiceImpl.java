package com.SpringBoot.Service;

import javax.jws.WebService;

@WebService(serviceName = "CxfWsService", portName = "CxfPort",
        endpointInterface = "com.SpringBoot.Service.CxfWsService")
public class CxfWsServiceImpl implements CxfWsService {


    public String sayHello(String name) {
        System.out.println("调用成功:"+name);
        return "sueecss"+name;
    }

}
