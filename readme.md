# SpringCloud-Netflix
## what is it?
SpringCloudNetflix通过自动配置和绑定环境Spring环境以及其他Spting编程模型  
为SptingBoot应用程序提供了Netflix OSS集成
## 什么是Netflix OSS
Netflix OSS是由Netflix公司主持开发的一套代码框架和库，目的是解决上了规模之后的分布式系统可能出现的一些有趣问题。
## 特色
1. 提供服务发现（Eureka），断路器（Hystrix），智能路由（Zuul）和客户端负载平衡（Ribbon）    
注1： 服务发现（Eureka）既是微服务的发现  
注2： 负载平衡就是分摊请求压力到分服务器上  

## 服务发现（Eureka）
用于发现Eureka微服务

### Eureka客户端的搭建-微服务
1. 构建你的pom文件
2. 编写boot程序，在类上加上这四个注解

	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaClient
	@RestController
	
其中只要加了EnableEurekaClient注解，并且classpath里面有`Spring Cloud Netflix` 和 `Eureka Core`的包  
那么项目启动时，会自动向Eureka server地址(默认值是：`http://localhost:8761`,可  在eureka.client.serviceUrl.defaultZone更改)    
这也算SpringBoot的特色吧  
3. 在你的resources文件创建application.propertoies用于配置客户端和Eureka之间的连接细节。   
详细属性和细节请参阅本项目的文件  

  


### Eureka服务器的搭建
1. 还是构建pom文件  
2. 编写boot应用程序  
注： Eureka服务器启动时会有一个带UI的主页面，以及普通Eureka功能的HttpAPI：`/eureka/*`  
注：Eureka服务器其实也是一个Eureka客户端，因此它会请求它自己来注册自己    
所以你还需要配置一个Eureka server地址来注册它，如果你不能提供这个地址的话，你的日志会记录一大堆  的`不能注册`的错误      
比较好的做法是运行多个Eureka服务器并使他们相互注册，这样Eureka可以变得更有弹性和可用性 ，具体的做法就是指定defaultZone的值了  

### 注意点
注1： 当客户端向Eureka注册时，提供的数据包括主机，端口，健康指示符URL，主页等。  
注2：现在发现的，配置文件名可以有一下形式：`application.yml`、`application.properties`   
注3：客户端重启做的改动可能无法立即反应到Eureka服务器，因为它有缓存   
注4：当客户端向客户端注册时，客户端会向服务器宣布我是健康的。而且是一直
           如果想让他在不健康的情况下宣布病危，可以在客户端的配置启动健康检查  
###  没讲到的
1. 先今把客户端停掉，在服务端的主页上还是为UP,虽然是需要一段时间的心跳包检测，但是没触发到，以后需要再做实验  
2. 不知道客户端注册到服务器有什么用，现在还是不知道。

## 断路器（Hystrix）
### 释义： 
Hystrix是Netflix创建的一个库 ,用于实现断路器 
### 特色
1. 当对特定的微服务请求失败达到某个阈值时(默认情况下为5秒内20次请求失败)，请求的线路会被终止（被称为开路或者称线路打开），在这种情况，你可以提供后备服务（就是备胎）  
2. 开路可以防止级联障碍，并愈合，拯救服务的负担和失败
3. 
备胎服务可以直接返回一个静态值，避免服务的继续失败堵塞请求以致服务器线程资源耗费



### 快速入门
1. 构建你的pom文件，放在server端。`跟EurekaServer端放在一起??`
添加一个groupId为`org.springframework.cloud` artifactId为`spring-cloud-starter-hystrix`  
2. 构建boot程序,只需要在boot程序加`@SpringBootApplication`and `@EnableCircuitBreaker`即可  
3. 创建一个bean并创建一个方法，方法上加`@HystrixCommand(fallbackMethod = "defaultStores")`注解  
   使用该注解的方法所在的bean会交由Hystrix断路器的代理来管理，客户端连接该代理时，断路器将判断何时间开路和闭合，以及发生故障该如何    
   要配置HystrixCommand注解，还可以写该注解的commandProperties属性，有关该属性的配置，可以参考
    Hystrix wiki[https://github.com/Netflix/Hystrix/wiki/Configuration]  
    PS：该Bean也被称为断路器了

### Propagating the Security Context or using Spring Scopes
待定，跟线程有关，大意是说，如果你想其他线程也起断路器的作用的话，该如何如何

### 什么时候会发生熔断
1. 服务终止，停止。那么一旦达到熔断的条件，线路将断掉。

### 健康指示器
断路器线路的健康状态也包含在使用Hystrix断路器的应用中，开启该应用，访问/healt即可

### 度量流
启动器依赖改为

	 <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-actuator</artifactId>
	 </dependency>
可以在管理端点开启度量流，访问`/hystrix.stream`试下吧

### Hystrix 的仪表盘（Dashboard）
Hystrix可以搜集所有使用HystrixCommand注解的bean，也就是断路器，Dashboard可以显示每个断路器的运行情况。  
要使用Dashboard，先加  
groupID=`Dashboardorg.springframework.cloud`   artifactId=`spring-cloud-starter-hystrix-dashboard`   
然后访问·` hystrix`地址，并设置客户端的/hystrix.stream端点  
	


          
 