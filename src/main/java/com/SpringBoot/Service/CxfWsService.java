package com.SpringBoot.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * 标注这是一个SEI接口
 */
@WebService(targetNamespace = "http://service.SpringBoot.com/",name = "CxfWsService")
public interface CxfWsService {

    @WebResult(name = "return", targetNamespace = "")
    @ResponseWrapper(localName = "sayHelloResponse",
            targetNamespace = "http://service.SpringBoot.com/",
            className = "sample.ws.service.SayHelloResponse")
    @RequestWrapper(localName = "sayHello",
            targetNamespace = "http://service.SpringBoot.com/",
            className = "sample.ws.service.sayHello")
    @WebMethod(action = "urn:SayHello")
    public String sayHello(@WebParam(name = "name", targetNamespace = "")String name);

}
