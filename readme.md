# spring boot 整合 cxh

# 一：专有名词

SEI接口：其实就是[ Service Endpoint Interface ]接口的简写了，简而言之，就是webService接口了

targetNamespace：翻译过来就是目标命名空间，用它可以区分不同的SOAP服务。

Ports： 它定义了web操作，能够执行的操作和所涉及到的消息。它的概念可以类比程序中的类，class

service:它其实就是Ports的集合

JAX-WS:这个代表了SOAP,他通常使用XML来传输数据

JAX-RS：这个代表REST，通常使用JSON来传输数据

注意：前面两者都是java提出的解决方案。其他语言未必有这些概念



# 二：步骤（JAX_WS）
1. 加入依赖
```
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
    <version>3.1.12</version>
</dependency>
```

2. 写cxf的配置类

   ```
   @Configuration
   public class CXFConfig {
       /**
        * cxf的中心配置支柱
        * 什么拦截啊，过滤器，都是在这个这里配置的
        */
       @Autowired
       private Bus bus;
       @Bean
       public Endpoint endpoint() {
           EndpointImpl endpoint = new EndpointImpl(bus, new CxfServiceImpl());
           //发布CxfServiceImpl的webService接口，接口地址后缀是Hello
           endpoint.publish("/Hello");
           return endpoint;
       }
   }
   ```

3. 写SEI接口

   ```
   /**
    * 标注这是一个SEI接口
    */
   @WebService(targetNamespace = "http://service.SpringBoot.com/",name = "CxfService")
   public interface CxfService {
   
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
   ```

   如上，这是一个简单的SEI接口，它就做一件事：接受调用方传过来的字符串，并返回一个字符串回去

4. 写SEI接口的实现

   ```
   @WebService(serviceName = "CxfService", portName = "CxfPort",
           endpointInterface = "com.SpringBoot.Service.CxfService")
   public class CxfServiceImpl implements CxfService {
       public String sayHello(String name) {
           System.out.println("调用成功:"+name);
           return "sueecss"+name;
       }
   }
   ```

5. 启动Springboot应用程序。然后。。写测试程序吧

   注意：如果你在application.properties写了`cxf.path=/Service`

   那么地址上，就要加上前缀

   ```
     String address = "http://localhost:10086/Service/Hello";
   
           String request = "<q0:sayHello xmlns:q0=\"http://service.SpringBoot.com/\"><name>Elan</name></q0:sayHello>";
           StreamSource source = new StreamSource(new StringReader(request));
           Service service = Service.create(new URL(address + "?wsdl"),
                   new QName("http://Service.SpringBoot.com/" , "CxfService"));
           Dispatch<Source> disp = service.createDispatch(new QName("http://Service.SpringBoot.com/" , "CxfPort"),
                   Source.class, Service.Mode.PAYLOAD);
           Source result = disp.invoke(source);
           String resultAsString = StaxUtils.toString(result);
           System.out.println(resultAsString);
   ```

# 三：步骤（JAX_RS）

1. maven依赖改为
    ​    ```
    ​    <dependency>
    ​        <groupId>org.apache.cxf</groupId>
    ​        <artifactId>cxf-spring-boot-starter-jaxrs</artifactId>
    ​        <version>3.1.12</version>
    ​    </dependency>
    ​    ```
2. 配置bean改为
        ```
           @Bean
            public Server rsServer() {
                JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
                endpoint.setBus(bus);
                endpoint.setAddress("/");
                endpoint.setServiceBeans(Arrays.<Object>asList(new HelloServiceImpl1(), new HelloServiceImpl2()));
                endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
                return endpoint.create();
            }
        ```
    
3. SEI接口改为
        ```
        @Path("/sayHello")
        @Service
        public interface CxfRsService {
                @GET
                @Path("/{name}")
                @Produces(MediaType.TEXT_PLAIN)
                String sayHello(@PathParam("name") String name);
        }
        ```
    看起来就很简单了，访问这个service的地址是sayHello
里面有一个接口sayHello，里面定义了response的type是`MediaType.TEXT_PLAIN`  也就是`text/plain`


4. 实现SEI接口
第一种实现方式

   ```
   public class CxfRsServiceImpl implements  CxfRsService {
   
       @Override
       public String sayHello(String name) {
          System.out.println("welcome to JAX_RS!!! your name is"+name);
          return "success";
       }
   
   }
   ```

简单粗暴

第二种实现方式，加了点特技在里面，Duang~

   ```
   @Path("/sayHello2")
   public class CxfRsServiceImpl2 implements  CxfRsService {
   
       @Override
       //覆写接口的path，实现自己的path
       public String sayHello(String name) {
          System.out.println("welcome to JAX_RS2!!! your name is"+name);
          return "success";
       }
   
   }
   ```

嘛，其实，就是覆写了接口的path。

5. 测试

   ```
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
   
   ```

其他自己去官方文档去看

# 四：其他

1. 可在application.properties 里面配置cxf
     如：

     1. 使用“ cxf.path ”属性自定义CXFServlet URL模式。

        使用了这个属性，所有要访问的webService地址，前面都会加上这个属性。

     2. 使用“ cxf.servlet.init ”map属性来自定义CXFServlet属性,例如“services-list-path”（默认位于“/ services”）

     3. 使用“ **cxf.jaxrs.server.path** ”属性自定义JAX-RS服务器端点地址（默认为“/”）

     4. 使用“ **cxf.jaxrs.component-scan** ”属性从自动发现的JAX-RS根资源和提供程序创建JAX-RS端点，这些资源和提供程序标记为Spring组件（使用Spring @Component注释或从@Bean方法创建并返回）。

2. jax-RS实现的方式默认没有生成wsdl文档的。当然，你想要的话。也不是不行。

     加个依赖，然后就OK了

     ```
      <!--jax-rs 的wsdl文档生成工具-->
           <dependency>
               <groupId>org.apache.cxf</groupId>
               <artifactId>cxf-rt-rs-service-description</artifactId>
               <version>3.2.6</version>
           </dependency>
     ```

     不推荐直接复制粘贴，关键是`cxf-rt-rs-service-description`，去maven库查一下就行

     事实上，你可以直接用浏览器访问rs的接口

     而你在抓包软件看请求和返回数据时，你只能看到。。。直接明了的请求和返回数据。

     不像传统的SOAP协议在外面还有包一层xml.

