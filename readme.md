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
断路器线路的健康状态也包含在使用Hystrix断路器的应用中，开启该应用，访问/health即可  
** 但是我自己新建了一个项目，用于断路器，访问该项目的/healt是404，未解决**    
**已解决，是我的锅，boot程序所在的包和组件注解包不是同一个基本包，扫描不到**  


### 度量流
启动器依赖改为  

	 <dependency>
	        <groupId>org.springframework.boot</groupId>
	        <artifactId>spring-boot-starter-actuator</artifactId>
	 </dependency>
可以在管理端点开启度量流，访问`/hystrix.stream`试下吧  
**注意的说，要访问hystrix.stream这个端点的话，则对应的断路器要工作一次，否则会报503，服务不可用**

### Hystrix 的仪表盘（Dashboard）
Hystrix可以搜集所有使用HystrixCommand注解的bean，也就是断路器，Dashboard可以显示每个断路器的运行情况。  
要使用Dashboard，先加  
groupID=`Dashboardorg.springframework.cloud`   artifactId=`spring-cloud-starter-hystrix-dashboard`   
然后访问·` hystrix`地址，并设置客户端的/hystrix.stream端点  
**结果能正常访问，但是怎么设置/hystrix.stream端点，前缀是什么？** 
**就设置你使用@HystrixCommand微服务地址**  

### Turbine
Turbine是一个应用程序，它将使用的/hystrix.stream端点汇总到一个用于Hystrix仪表板的组合/turbine.stream中。  
个别断路器可以通过Eureka获得  
要使用这个Turbine  
1. 在pom里面加`spring-cloud-starter-turbine`依赖。  
2. 在boot程序加@EnableTurbine注解  
注意：默认情况下，Turbine会在Eureka里面查找客户端的homePageUrl ，然后获知端口，组合/hystrix.stream  
这意味着如果`spring-boot-actuator`在自己的端口上运行，访问 `/hystrix.stream`将失败，要让Turbine找的正确的端口  
你需要添加这个配置  

	eureka:
	  instance:
	    metadata-map:
	      management.port: ${management.port:8081}
	
其他略

## 客户端负载平衡：Ribbon
含义:Ribbon是一个客户端负载均衡器，他可以让你对HTTP和TCP的客户端有很大的控制权。  
Ribbon一个重要的概念是客户端的命名，每一个平衡负载器是组件的一部分
## 如何引入Ribbon 
使用groupID=`org.springframework.cloud` and artifactId=`spring-cloud-starter-ribbon`
## 定制你的Ribbon Client（代码配置）
题外话：你可以使用`<client>.ribbon.*`来配置Ribbon Client某些位。  
使用@RibbonClient注解一个普通类使之成为Ribbon Client客户端的声明配置类（`RibbonClientConfiguration`）  
这个声明配置类需要引用其他配置类，而这个配置类才是Ribbon真正的生效配置  
默认情况下，Ribbon会默认注入几个bean  (以下格式为：bean类型：bean名：类名)
1. IClientConfig ribbonClientConfig: DefaultClientConfigImpl
2. IRule ribbonRule: ZoneAvoidanceRule
3. IPing ribbonPing: NoOpPing
4. ServerList<Server> ribbonServerList: ConfigurationBasedServerList
5. ServerListFilter<Server> ribbonServerListFilter: ZonePreferenceServerListFilter
6. ILoadBalancer ribbonLoadBalancer: ZoneAwareLoadBalancer  
你可以在配置类重新注入这些bean  

##定制你的Ribbon Client （属性配置） 
Spring Cloud Netflix 1.2.0以后，支持用属性配置Ribbon clients。  
注意：这种配置优先于代码配置，优先于Spring Cloud Netflix提供的默认类。  
支持的属性列表如下所示：（注意，属性的前缀这里没有列出，正常应该是`<clientName>.ribbon.`  
1. NFLoadBalancerClassName: should implement ILoadBalancer  
2. NFLoadBalancerRuleClassName: should implement IRule  
3. NFLoadBalancerPingClassName: should implement IPing  
4. NIWSServerListClassName: should implement ServerList  
5. NIWSServerListFilterClassName should implement ServerListFilter  
示例的配置如下  

	users:
  		ribbon:
    	NFLoadBalancerRuleClassName:com.netflix.loadbalancer.WeightedResponseTimeRule
这个的意思的是为服务名为users设置IRule

## Ribbon和Eureka互联
￥#@%#￥%……%￥&%@￥%#%我不知道官网在讲什么鬼

## 未解之谜
1. 只学会了配置RIbbon客户端配置，然后呢？坑！

#Feign
含义：这是一个声明式的web服务客户端。只需创建一个接口并对其进行注解，就可以使用了  
	 支持JAX-RS注解和JAX-RS注解，支持可插拔的编码器和解码器。
## 搭建入门环境
1. 构建你的pom文件，使用groupID=`org.springframework.cloud` and artifactID=`spring-cloud-starter-feign`
2. 编写boot程序，详情请见Feign分支
3. 编写客户端接口  
几个注意点  
1：客户端接口对应的bean  Name是类名的完全限定名称，这个bean还会创建一个别名，默认情况下，别名=@FeignClient的value(name)属性+FeignClient，你可以使用@FeignClient的Qualifier属性来更改别名  
2: 编写了客户端后，Ribbon client需要查找到客户端服务的物理地址，如果你的应用是一个Eureka客户端，则它会解析Eureka里面已经注册好的服务。如果你不想使用Eureka，也可以简单的外部配置中配置服务器列表  
3： @FeignClient注解有一个属性configuration 可以声明额外的配置  
## Feign配置
SpringCloudNetflix为feign提供以下默认的bean（BeanType beanName: ClassName）  
1. Decoder feignDecoder: ResponseEntityDecoder (包装了一个 SpringDecoder)  
2. Encoder feignEncoder: SpringEncoder  
3. Logger feignLogger: Slf4jLogger  
4. Contract feignContract: SpringMvcContract  
5. Feign.Builder feignBuilder: HystrixFeign.Builder  
6. Client feignClient: 如果ribbon可用，则这个bean是一个LoadBalancerFeignClient，否则默认  
_______________________________________________________________________
feign clients 有两种：OkHttpClient 和ApacheHttpClient 
_______________________________________________________________________
你可以设置`feign.okhttp.enabled=true` or `feign.httpclient.enabled=true` 并让他们在类路径上
另外，不提供以下bean，但如果在应用程序的环境中查找到以下bean，还是可以用这些bean来创建 feign client
1. Logger.Level
2. Retryer
3. ErrorDecoder
4. Request.Options
5. Collection<RequestInterceptor>  
创建这些Bean并把它放到配置类上，可以覆盖  

##手动创建 feign客户端
有些时候，使用上面的方式来建立客户端可能是没用的，这时候，就需要用Feign Builder API来创建Feigh客户端  
TODO 将代码复制到项目中并加上注释  

## Feign的Hystrix支持
如果Hystrix类class path上，那么默认情况下，Feign所有的方法都用断路器来包装。  
如果想禁用Feign上的Hystrix支持的话，请设置属性`feign.hystrix.enabled=false`  
好像有说要具体禁用每一个客户端的Hystrix应该怎么办，现在还不是很理解，之后再试试看  
TODO 代码记得补充  

## Feign Hystrix Fallbacks
Feign Hystrix支持在线路出现问题的时候使用回退，执行其他方法，以避免堵塞请求。  
要开启这个功能的话，只需要在@FeignClient注解上加一个fallback属性，属性值填回退的类名  
如果想要知道回退的原因是什么，你可以在创建回退类时实现一个FallbackFactory<HystrixClient>接口，在代码里面处理    
TODO 代码补上哦   
PS：回退的功能有一定限制，目前不支持`com.netflix.hystrix.HystrixCommand` and `rx.Observable`  


## .Feign日志
Feign会为每一个Feign客户端创建一个记录器？？默认情况下，记录器的名称是Feign客户端接口的完整类名全称
注：Feign只记录Debug级别的日志
我猜你可以更改？
`logging.level.project.user.UserClient: DEBUG`  on `application.yml`  
你可以为每一个客户端配置 Logger.Level对象来告诉Feign要记录多少日志
TODO 代码补上，注释补上


# Zuul 路由
路由在微服务中是一个完整的组成部分。用于处理URL和微服务之间的映射    
比如说：`\`会映射到你的web应用程序、`\api\user`映射到用户服务、`\api\shop`映射到商店服务  
Zuul是Netflix提供的基于JVM的路由器和服务器端负载均衡器。	
Netflix的Zuul提供以下功能  
1. 验证  
2. Insights  
3. 压力测试  
4. 金丝雀测试（一部分服务器升级到最新版本，然后没问题再全部部署）  
5. 动态路由  
6. 服务迁移  
7. 卸下负载，减轻负载压力  
8. 安全   
9. 静态响应处理  
10. 流量管理  
Zuul支持用任何JVM语言编写规则和过滤器，支持java和Groovy  


> 注意：在新版本中，`zuul.max.host.connections`配置属性已经被两个新属性取而代之  
> 注意：所有路由默认的Hystrix隔离模式是`SEMAPHORE`,如果这种隔离模式是首选的？？，`zuul.ribbonIsolationStrategy`可以隔离模式为`THREAD `
## 快速开始
### 1. 啥也别多说了，拿出你的Pom文件，加上依赖吧  

	 <dependency>  
        <groupId>org.springframework.cloud</groupId>  
        <artifactId>spring-cloud-starter-zuul</artifactId>  
     </dependency>  

### 2. 植入Zuul的反向代理  
 SpringCloud已经嵌入了一个Zuul代理，为了使用它，在boot程序里面加上@EnableZuulProxy注解，并将本地调用转发给对应服务  
 Zuul代理通过Ribbon来找到转发的实例，并且所以请求都会在Hystrix中运行，这意味着所有故障都会显示在Hystrix度量标准中，一旦线路开了，代理不会再联系服务
> `spring-cloud-starter-zuul` 不包含发现客户端，你可以使用Eureka来帮忙干活，在classpath添加Eureka的依赖吧  
> 要跳过自动添加的服务，可以在`zuul.ignored-services`设置一些模式，匹配这个模式的服务将被忽略。如果一个服务满足这个模式，但是又包含在路由映射中，那么它还是会被忽略？？
   
### 3. 配置        
 为了增加或者改变代理路由，你可以在外部配置添加一以下配置
	
	 zuul:
	  routes:
	    users: /myusers/**  
		----as  application.yml

上面的配置意思是说，如果你在浏览器请求`/myusers`这个url，那么它将会转发到users服务

更细粒度的配置可以这么配
	
	 zuul:
	  routes:
	    users:
	      path: /myusers/**
	      serviceId: users_service
这意味着访问`/myusers/**`将被转发到users_service这个服务。并且路由必须有一个路径  
PS：`/myusers/*`只匹配一个级别，但是`/myusers/**`分层次匹配    
PS:`serviceId`也可以改为URL，比如说`url: http://example.com/users_service`  
PS:这些简单的URL路由，断路器起不来作用，Ribbon也不起作用，所以还是推荐用serverID来作为路由映射
目前需要在Ribbo中禁用Eureka支持？？
PS:zuul.routes条目实际是用于绑定到ZuulProperties类型的对象，如果你看看这个对象的属性话，可以发现有一个retryable的标志，在配置文件加上，把值设置为true可以使客户端重试失败的请求
（如果需要，还可以在Ribbo客户端配置中修改重试操作的次数）