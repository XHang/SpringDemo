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
