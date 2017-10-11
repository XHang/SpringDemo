该项目为SpringAOP的示例程序
====================

	1：将由浅入深的讲解SpringAOP的各种特性
	2：采用SpringBoot引导，web应用结构，主要是为了演示控制器层的自动代理。
	3:顺便了解一下SpringBoot的自动加载配置文件的功能，以及git如何上传分支，分支文件夹就是主要代码
	4：代码合并到SpringDemo的分支上
暂时就先这样。

项目没提到的
---------------------
这个项目将使用SpringBoot来代理控制层。不是用传统的xml方式。  
如果你要用传统的xml方式来代理控制层。
注意要将<aop:aspectj-autoproxy proxy-target-class="true"/>  
加在SpringMVC配置文件里面，而不是普通的bean文件里面  
原因？听说Springmvc的控制器bena是专门由mvc的容器来管理的  

## 你知道吗？
1. around方法如果定义返回值为void的话代理方法是不能返回数据给调用着的
2.  切面表达式的完整解释如下  

eg:`execution(* com.sample.service.impl..*.*(..))`

解释如下：  

`execution（）`:表达式的主体；
第一个`*`符号:表示返回值的类型任意；
`com.sample.service.impl`:	AOP所切的服务的包名，即，我们的业务部分  
包名后面的`..` ： 	表示当前包及子包  
第二个`*“`:	表示类名，*即所有类。此处可以自定义，下文有举例  
`.*(..) `:	表示任何方法名，括号表示参数，两个点表示任何参数类型  

基本语法格式如下：

	execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)  除了返回类型模式、方法名模式和	参数模式外，其它项都是可选的。
