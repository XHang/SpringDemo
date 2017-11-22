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
释义： Hystrix是Netflix创建的一个库  
特色
1. 当对特定的微服务请求失败达到某个阈值时(默认情况下为5秒内20次请求失败)，请求的线路会被终止（被称为开路或者称线路打开），在这种情况，你可以提供后备服务（就是备胎）  
2. 开路可以防止级联障碍，并愈合，拯救服务的负担和失败
3. 
The fallback can be another Hystrix protected call, static data or a sane empty value. Fallbacks may be chained so the first fallback makes some other business call which in turn falls back to static data.
4.
	



          
 