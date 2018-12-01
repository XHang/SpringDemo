# 项目：Springmvc
## 项目介绍：  
这个项目主要讲解SpringMVC怎么使用的，以及一些进阶教程  
主要内容有：
1：配置Spring的控制器方法...以及很少用了   
2：注解Spring的控制器方法  
3：解答是否只要加json的依赖就不需要加任何配置，responseBody就可以返回json数据  
4 ：如果有可能的话，解答一下为什么SpringMVC能根据返回值确定输出格式是什么？  
5：SpringMVC的文件上传  
6 : 解答文件上传怎么解决文件名的乱码问题  
7：拦截器的实现-日志版    
8：rest风格的URl  
9：另外一种URL映射控制器的方法
10 : mvc的四种跳转方式  
11：mvc的请求参数和实际参数名不一致的情况  
12 ：发送一个对象到服务器  

13：搞定get和post的乱码

## 如何开始？  
如果你只是想看下注解版的控制器，不需要改任何代码，直接clean tomcat7:run就可以运行项目  
敲入`http://localhost:8080/SpringMVC/annotation.do`即可见证奇迹    

如果想看配置版的，你需要把SpringMVC.xml里面引入的其他配置文件改为配置版的配置文件      
然后运行程序，键入改地址`http://localhost:8080/SpringMVC/config.do`即可  

如果想玩下@ResponseBody的效果，尝试用以下两个url    
`http://127.0.0.1:8080/SpringMVC/responseExample`    
 `http://127.0.0.1:8080/SpringMVC/getString.do`  

 想玩下拦截器？随便哪个网址都可以啦  

 想看一下高端大气上档次的rest请求？  
 输入网址：`http://localhost:8080/SpringMVC/rest/参数值/参数值`  
 可以OK  

 想看一下用其他方式访问控制器方法？  
 骚年，点击这个链接吧`http://localhost:8080/SpringMVC/mapping?method=otherMapping`  
 其实就是要用参数来指定访问的是哪一个方法。毫无卵用的样子

 什么，想看下四种跳转方式，少年，接刀
 NO1:使用request对象forword=`http://localhost:8080/SpringMVC/requestForWord`  
 NO2:使用response对象重定向=`http://localhost:8080/SpringMVC/responseRedirection`  
其他MVC提供的跳转请访问`http://localhost:8080/SpringMVC/mvcJump`  
需要搭配代码食用哦

顺便看下mvc的别名，访问这个url`http://localhost:8080/SpringMVC/alias?keyWord=fuck`  
顺便告诉你一声，你传keyWord为参数名，而服务器实际接收的参数是fuck。

演示下怎么发对象到服务器，划重点，要考的
点击登录`http://localhost:8080/SpringMVC/sendObject?username=%钟国典&password=%中国电视史`
如果对象里面又嵌套另一个对象，需要用属性名.对象属性来传

乱码问题演示下的话，进入这个网站操作  
`http://localhost:8080/SpringMVC/Encode.jsp`  
可惜Spring提供的`org.springframework.web.filter.CharacterEncodingFilter`  
只能处理post请求的数据。   
不过某人已经实现了一个可以处理get乱码的过滤器了  

PS:  
​		
​	<plugin>
​	      <groupId>org.apache.tomcat.maven</groupId>
​	      <artifactId>tomcat7-maven-plugin</artifactId>
​	      <version>2.2</version>
​	      <configuration>
​	      	<warSourceDirectory>WebRoot</warSourceDirectory>
​	      </configuration>
  </plugin> 

可以设置项目用tomcat插件运行，其中warSourceDirectory的配置指定的是运行的web路径

## 疑难解答
1. 只加json的依赖，就想不通过任何配置来把方法的返回值变成json数据，你还是太年轻了  
    除非你用的是SpringBoot的自动配置，或者用了这个配置<mvc:annotation-driven />

## SpringMVC jsonp的设置
1. 什么是jsonp？其实就是用来跨域的请求数据类型，ajax的daatType加了jsonp就可以实现跨域。

2. 吐槽，对于跨域请求，其实已经有请求头来设置跨域了，再弄个jsonp实在是多次一举。


 3. 挖坑，待定

##  web端知识点
1. request.getParameter("参数")  如果参数没有的话，返回null；

## Spring MVC 接受特殊类型

1. 日期类型

   如果你只是在参数上写接受一个日期类型，没有任何处理。

   那么前端需要怎么传呢？

   可以在data里面传一个date对象，即

   ```
    $.ajax({
            url:getRoot()+"receiveDate",
            data:{
                date:new Date()
            },
            success:function (data) {
            alert("接受到后台传输的数据是"+data);
           }
        })
   ```

   实际上，这个日期参数是个标准日期的字段，格式类型为：

   `Fri Jul 06 2018 11:27:09 GMT+0800 (中国标准时间)`

   当然需要经过URL转码才能发过去。

   这种日期格式可以被SpringMVC接受。

   当然这种方式很原始，现在更多的是弄一个转换器，来转对应的参数



## Spring MVC JSON反序列化设置

这一章主要是讲，在SpringMVC中，怎么自定义地将JSON数据序列化成对象。

业务举例：前端传来了一段json数据

```
{
  "desc": "这是测试例子",
  "status": "0"
}
```

一般来说，你要把这段数据保存在一个JAVA的实体类中。而且这个实体类必须是有两个属性

String desc

String status

那好，问题来了，实际上你的实体类没有一个String的status，而是一个实体类的Status.

这个实体类内容是

String id

String name

现在的问题是，怎么把json里面的status,变成实体类Status对象，并填充到响应对象里面？

> 感觉举得例子有点老太婆裹脚布，又臭又长。

问题的解决，就是用`@JsonSerialize(using = xxxx.class)`注解，用在字段的get方法

xxxx类，要你自己写一个类，去继承`JsonSerializer`类

不逼逼了，直接亮代码

```
public class DictDeserialize extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String id = p.getText();
        //拿到json的字段名
        String fieldName = p.getCurrentName();
        //拿到反序列化的对象
        Object target = p.getCurrentValue();
        Class clazz = target.getClass();
        Class type = null;
        try {
            Field field = clazz.getDeclaredField(fieldName);
             type = field.getType();
             //要保证Status类含有一个字符串参数的构造方法
            Constructor constructor = type.getDeclaredConstructor(String.class);
            //
            return constructor.newInstance(id);
        } catch (NoSuchFieldException e) {
            throw new SystemException("即将反序列化的字段【%s】找不到，请校验你的类【%s】",fieldName,clazz);
        } catch (NoSuchMethodException e) {
            throw new SystemException("即将反序列化的字段【%s】，类型为【%s】找不到参数是(String id)的构造器，请校验你的字典类",fieldName,type);
        } catch (Exception e) {
            throw new SystemException(e,"反序列化字段【%s】失败,字段所属的类是【%s】",fieldName,clazz);
        }
    }

```

这个代码的目的就是将json里面的一个字符串类型的字段，反序列成java对象.



## Spring MVC JSON  序列化设置

既然有反序列化，那当然有序列化咯

业务需求就是一个对象，有一个时间属性，你想控制这个时间在json中的表现形式，比如说，显示成数字形式的时间戳。这个时间，就可以让新的注解出场了

```
@JsonSerialize(using =xxx.class)
```

xxx也需要你自己继承JsonSerializer方法

代码如下

```
/**
 * 时间戳转换器
 * 不知为何，默认的Timestamp，json转换后是2018-10-22T12:51:23.000+0000
 */
public class TimeStampSerializer extends JsonSerializer {

    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }


    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        }else if (value instanceof Date){
            Date date = (Date) value;
            gen.writeNumber(date.getTime());
        }else if (value instanceof Timestamp){
            Timestamp timestamp = (Timestamp) value;
            gen.writeNumber(timestamp.getTime());
        }else{
            throw new BusinessException("不支持除Date和Timestamp之外的json序列化，请修改程序，或者使用其他方法,你想序列化的类型是【%s】",value.getClass().getName());
        }
    }
}
```

很简单吧，就不多解释了



以上

## Spring MVC 数据校验

业务代码写校验判断很烦？

一大堆if语句乱飞?

骚年，你需要这个`org.hibernate.validator`
=======
## 使用校验框架校验参数的有效性

为什么使用校验框架来检验接口入参的有效性

这还用多说？

直接上解决方案

1. 加依赖

   ```
   自己找去
   ```

2. 使用校验框架检验入参

   假设有一个方法定义如下

   ```
   
       @PostMapping
       public void save(@RequestBody @Validated User user, BindingResult errForm, HttpServletRequest request, HttpServletResponse response) {
           .....
       }
   ```

   如代码所示，想对入参User的属性进行校验，控制字段必传与否

   第一步就是在你想校验的参数前面加`@Validated`

   第二步就是在被校验参数的后面再另加一个参数BindingResult errForm

   校验的结果，SpringMVC放在这个参数里面了，只要对这个参数进行检查，就能知道那个字段校验不通过，是否要终止调用。

   那么怎么告诉SpringMVC我要对哪个字段进行校验呢？

   其实很简单，祭出几大注解，只要加注解在对应字段上，就能校验字段

   1. `@NotNull` 非空注解。注意，对于字面量可不适用，比如说int类型的字段，double类型的字段。

      小心SpringMVC给你抛出一个不知所云的异常

   2. `@NotEmpty` 非空容器注解，这个只能加在容器类型字段上，比如说,List，Set,Map及数组

      用于校验容器不能没有元素。即空容器是不允许的

   3. `NotBlank`针对字符串的一个校验注解，校验字符串不能为空串；

   4. @Valid针对实体类的一个注解，目前看到的用法就是在嵌套对象属性上面加，则嵌套对象里面的属性校验注解也会生效，如果不加，就不会生效

   以上
