## 说明
该项目是Feign的演示项目，包括  
1. Fegin的基本使用  
2. Fegin组合断路器，Ribbon使用  
  如果要开启演示的话，你需要配置
3. Eureka的服务器地址，用于注册这个微服务
4. 确保Eureak注册了一个Application name为User的微服务。
5. 确保这个User的微服务有一个对外的Http接口，地址为`/server/getXml`
## 知识点  
1. 如果fegin客户端中有一个http get方法，其参数很多，则需要加@RequestParam  
  不然当创建bean的时候，会抛异常：` Method has too many Body parameters:`  

2. 如果Fegin客户端的RPC方法要传一个对象参数过去，则这个对象参数前面要加@ResponseBody注解

  详情请看com.ribbon.server.UserClient.addUser方法

  特别说明的是，如果这个对象是非必传的，你调用这个RPC的Service时，对象参数不能传null，起码得传一个空对象，不然Fegin会抛`Body parameter xx was null`xx是方法参数的索引

3. Fegin在启动时默认会创建如下几个类供Fegin使用（格式是：`BeanType` beanName: `ClassName`）

   - `Decoder` feignDecoder: `ResponseEntityDecoder`
     -  (里面包装了一个 `SpringDecoder`，用于将服务器返回的数据进行解码，实例化成java对象)
   - `Encoder` feignEncoder: `SpringEncoder`
   - `Logger` feignLogger: `Slf4jLogger`
   - `Contract` feignContract: `SpringMvcContract`
   - `Feign.Builder` feignBuilder: `HystrixFeign.Builder`
   - `Client` feignClient: if Ribbon is enabled it is a `LoadBalancerFeignClient`, otherwise the default feign client is used.

   这些默认的Bean可以在Feign的配置类中重写覆盖掉

4. 以及Fegin默认没有提供这些Bean

   - `Logger.Level`
   - `Retryer`
   - `ErrorDecoder` （用于在服务端返回异常信息时，将其转码，要求http状态码必须不是20x）
   - `Request.Options`
   - `Collection<RequestInterceptor>` (可以拦截Feign的每一个http请求，在里面加http请求头)


   同样你也可以在Feign的配置文件中配置这些Bean。