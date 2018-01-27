## 说明
该项目是Feign的演示项目，包括  
1. Fegin的基本使用  
2. Fegin组合断路器，Ribbon使用  
如果要开启演示的话，你需要配置
1. Eureka的服务器地址，用于注册这个微服务
2. 确保Eureak注册了一个Application name为User的微服务。
3. 确保这个User的微服务有一个对外的Http接口，地址为`/server/getXml"`  
## 知识点  
1. 如果fegin客户端中有一个http get方法，其参数很多，则需要加@RequestParam  
不然当创建bean的时候，会抛异常：` Method has too many Body parameters:`  

