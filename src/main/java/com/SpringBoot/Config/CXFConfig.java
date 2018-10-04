package com.SpringBoot.Config;

import com.SpringBoot.Service.CxfRsServiceImpl;
import com.SpringBoot.Service.CxfRsServiceImpl2;
import com.SpringBoot.Service.CxfWsServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.JAXRSServiceImpl;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import java.util.Arrays;

@Configuration
public class CXFConfig {
    /**
     * cxf的中心配置支柱
     * 什么拦截啊，过滤器，都是在这个这里配置的
     */



    @Autowired
    private Bus bus;
    //-------------------JAX-WS标准的实现-------------------------
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new CxfWsServiceImpl());
        //发布CxfServiceImpl的webService接口，接口地址后缀是Hello
        endpoint.publish("/jaxws");
        return endpoint;
    }
    //-------------------JAX-WS标准的实现-------------------------
    @Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/jaxrs");
        endpoint.setServiceBeans(Arrays.<Object>asList(new CxfRsServiceImpl(), new CxfRsServiceImpl2()));
        return endpoint.create();
    }
    //-------------------JAX-RS标准的实现-------------------------

}
