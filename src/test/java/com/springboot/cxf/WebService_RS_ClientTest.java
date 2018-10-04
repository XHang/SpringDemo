package com.springboot.cxf;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.staxutils.StaxUtils;
import org.junit.Assert;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.net.URL;

public class WebService_RS_ClientTest {

    public static void main(String [] args) throws Exception {
        int port = 10086;
        WebClient wc = WebClient.create("http://localhost:" + port + "/Service/jaxrs/");
        wc.accept("text/plain");
        // HelloServiceImpl1
        wc.path("sayHello").path("ApacheCxfUser");
        String greeting = wc.get(String.class);
        System.out.println("webService返回的数据是"+greeting);

        // Reverse to the starting URI
        wc.back(true);

        // HelloServiceImpl2
        wc.path("sayHello2").path("ApacheCxfUser");
        greeting = wc.get(String.class);
        System.out.println("webService返回的数据是"+greeting);
    }
}
