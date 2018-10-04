package com.springboot.cxf;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.staxutils.StaxUtils;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.net.URL;

public class WebService_WS_ClientTest {

    public static void main(String [] args) throws Exception {

        String address = "http://localhost:10086/Service/Hello";

        String request = "<q0:sayHello xmlns:q0=\"http://service.SpringBoot.com/\"><name>Elan</name></q0:sayHello>";


        StreamSource source = new StreamSource(new StringReader(request));
        Service service = Service.create(new URL(address + "?wsdl"),
                new QName("http://Service.SpringBoot.com/" , "CxfWsService"));
        Dispatch<Source> disp = service.createDispatch(new QName("http://Service.SpringBoot.com/" , "CxfPort"),
                Source.class, Service.Mode.PAYLOAD);
        Source result = disp.invoke(source);
        String resultAsString = StaxUtils.toString(result);
        System.out.println(resultAsString);
    }
}
