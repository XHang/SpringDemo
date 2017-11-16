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

###  开始入门
1. 修改你的pom文件




## 出错
1. 第一个出错就是复制官网的POM代码构建时报错了。。
      我简直是日了哈士奇了。这么大的官方网站，直接拷贝下来POM代码居然die了。  
    最后是怎么解决的呢？没办法，我看到官网给的POM代码中，有几个配置的groupId没有，顺手补上了。。搞定
    求解，这是新特性，我的电脑不支持，还是官网脑袋被门挤了？ 
    
 
 
 
 