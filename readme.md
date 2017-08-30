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
TODO ：之后会写一个不基于框架的过滤器

PS:  
		
	<plugin>
          <groupId>org.apache.tomcat.maven</groupId>
          <artifactId>tomcat7-maven-plugin</artifactId>
          <version>2.2</version>
          <configuration>
          	<warSourceDirectory>WebRoot</warSourceDirectory>
          </configuration>
  </plugin> 
  
可以设置项目用tomcat插件运行，其中warSourceDirectory的配置指定的是运行的web路径

## 疑难解答
1. 只加json的依赖，就想不通过任何配置来把方法的返回值变成json数据，你还是太年轻了  
    除非你用的是SpringBoot的自动配置，或者用了这个配置<mvc:annotation-driven />

