# 项目：SpringCloud
## 项目介绍：  
 SpringCloud提供了快速建立分布式环境的工具
 1. 配置管理
 2. 探测'服务'
 3. 断路器？
 4. 智能路由!
 5. 微代理
 6. 控制总线
 7. 一次性令牌
 8. 全局锁？
 9. 选举主服务器（领导选举...)
 10. 分布式会话
 11. 集群状态
 SpringCloud是在SpringBoot上构建的
 本次学习的目标是：4
 
##  构建步骤
1. 构建项目的POM文件，根据官网的快速入门


##组件--SpringCloudConfig
作用：为分布式系统的外部配置提供服务端和客户端支持。  
特征： 
1. Http，用于外部配置的api资源  
2. 加密和解密属性值  
3. 只需使用@EnableConfigServer轻松嵌入到Spring Boot应用程序中  
4. 使用远程属性来初始化Spring环境  
5. 只要SpringBoot的启动器和SpringConfigClient在类路径上，所有SpringBoot程序都会访问    
`http：// localhost：8888`（默认值为`spring.cloud.config.uri`）上的配置服务器  
6. 这个组件分为Server端和Client端  
	server端就是Client端用来请求配置的。  
	Client端就是微服务，任何需要从远程获取配置数据的应用都算客户端  

###  ConfigServer端部署。
1. 构建pom文件，官网并没有给出该pom文件的示例，建议搜索  
2. 构建Server端自身的配置文件。一般只需要在resources文件新建一个application.properties文件。  
	请参考该git仓库的另一个分支，`spring-cound-config-server`
3. 编写boot程序
4. 运行
5. 浏览器访问配置服务器的根目录，如果能得到一个json数据，那么基本的没问题的。

### ConfigClient端改造
注意：这个Client前提要是SpringBoot应用程序  
1. 在你的构建的pom文件补上示例代码
2. 编写一个boot程序，在boot程序上面加一个@EnableAutoConfiguration注解
3. 编写Client和Server端互联的一些配置细节文件，放在resources文件夹，起名为application.properties
	文件内容参考本项目

### 注意点
1. 配置文件的git仓库中，配置文件名是有一定格式的。
 
	/{application}/{profile}[/{label}]
	/{application}-{profile}.yml
	/{label}/{application}-{profile}.yml
	/{application}-{profile}.properties
	/{label}/{application}-{profile}.properties 

	客户端可以自己选择用哪个配置文件，这主要是通过客户端的application.properties里面的三个键值对来实现的  
	这三个键值对分别是 `spring.application.name` ,`spring.cloud.config.label`,`spring.cloud.config.profile`  
	如果不指定会访问哪个文件？配置仓库里面的`application.yml  or aplication.properties`  

2. 如果客户端有自己特有的键值对怎么办，这个键值对不想设置到配置仓库里面。
	那么只需要在自己的配置文件里面加上就行了
	
	
		
		



## 出错
1. 第一个出错就是复制官网的POM代码构建时报错了。。  
      我简直是日了哈士奇了。这么大的官方网站，直接拷贝下来POM代码居然die了。   
    最后是怎么解决的呢？没办法，我看到官网给的POM代码中，有几个配置的groupId没有，顺手补上了。。搞定  
    求解，这是新特性，我的电脑不支持，还是官网脑袋被门挤了？ 
    
 
 
 
 