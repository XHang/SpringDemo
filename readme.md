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

### Eureka客户端的搭建-微服务
1. 构建你的pom文件
2. 编写boot程序，在类上加上这四个注解

	@Configuration
	@EnableAutoConfiguration
	@EnableEurekaClient
	@RestController
	
其中只要加了EnableEurekaClient注解，并且classpath里面有`Spring Cloud Netflix` 和 `Eureka Core`的包
那么项目启动时，会自动向Eureka server地址(默认值是：`http://localhost:8761`,可在eureka.client.serviceUrl.defaultZone更改)  
这也算SpringBoot的特色吧

## Eureka服务器的搭建
1. 还是构建pom文件
2. 编写boot应用程序
注： Eureka服务器启动时会有一个带UI的主页面，以及普通Eureka功能的HttpAPI：`/ eureka / *`
注：Eureka服务器其实也是一个Eureka客户端，因此它会请求它自己来注册自己  
所以你还需要配置一个Eureka server地址来注册它，如果你不能提供这个地址的话，你的日志会记录一大堆的`不能注册`的错误  
比较好的做法是运行多个Eureka服务器并使他们相互注册，这样Eureka可以变得更有弹性和可用性


