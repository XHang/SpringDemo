SpringBoot
===========

# 一：快速入门

步骤：

1. ：新建一个Maven项目
2. 在pom文件的project标签下补上

```
<parent>
	<groupId>org.springframework.boot</groupId>
	 <artifactId>spring-boot-starter-parent</artifactId>
	 <version>1.5.4.RELEASE</version>
</parent>
```

3. 引入此依赖

```
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```

4. 新建两个类文件，一个作为控制器类，一个作为启动类
   控制器类:写一个普通的映射方法即可，能在启动后通过浏览器访问到即可,对了，
   启动类:补上`@EnableAutoConfiguration`注解,加个主函数，里面运行
   `SpringApplication.run(控制器类名.class);`
5. 运行主函数
   看到控制台输出：`initialization completed in 17 ms`
   看看 Tomcat initialized with port(s): 8080 (http)端口
   接下来应该知道怎么做了吧？
   访问你的控制器方法，走起。
   恭喜，环境搭建完毕

## 1.1 快速入门解释

**1：为什么要搞一个parent标签？**

答：有两个好处。

1. 添加parent pom（父依赖）已有的依赖时不用加version，避免版本冲突

2. 获得一些常见的默认值设置，你不用自己覆写。这些默认设置有

   1.默认编译器级别是1.6.通过以下配置可以覆写该配置

   ```
   <properties>
       <java.version>1.8</java.version>
   </properties>
   ```

 2. 源代码编码是` UTF-8`

 3. 聪明的资源过滤？   不理解

 4. 聪明的插件配置 

 5. 由于默认配置文件接受Spring样式占位符`（$ {...}）`，

    Maven过滤更改为使用@ .. @占位符

    （您可以使用Maven属性resource.delimiter覆盖它）。不理解

	6. boot提供了一个属性可以让你选择依赖的其他版本

    example,parent有一个`log4j2`  ` 2.7`的版本，你觉得不爽，你可以添加一些配置

    ```
    <properties>
       	<log4j2.version>2.9</log4j2.version>
    </properties>
    ```

		这样你在项目的依赖中添加一个log4j2,不用指定版本号，它默认就升级到了2.9版本 

7. 使用maven运行`spring-boot:run`命令可以直接启动项目

**2:引入的依赖spring-boot-starter-web包括什么？**
	答：包括Tomcat web 服务器和Springboot本身

**3：@EnableAutoConfiguration这个注解是什么鬼？**
	答：这个是`SpringBoot`根据你添加的`jar` 包依赖来猜测这个项目的Spring该如何配置（自动配置）
	上面pom文件不是加了个spring-boot-starter-web依赖嘛
	 Springboot注意到了那个web，所以它就猜测你正在开发web程序，顺便把配置给你解决了。
	 还要写xml文件吗？不用了，让xml见鬼去吧
	  那么，`@EnableAutoConfiguration`应该写在哪里？
	 写在`SpringApplication.run(类.class);`那个运行的类中

# 二：Spring Boot用jar包运行

简而言之：不用任何的外部容器，直接在jar包就可以独立运行一个web应用。

步骤：

1. 在项目的pom文件添加一个插件配置

   ```
   <build>
   	<plugins>
           <plugin>
    			<groupId>org.springframework.boot</groupId>
   			 <artifactId>spring-boot-maven-plugin</artifactId>
           </plugin>
      	</plugins>
   </build>
   ```

 2. 运行package命令。查看生成的包，大小应该超过5mb了，如果你看到的只有5kb。。。
    把上面的parent pom (父依赖)加上去吧	

    > 其实没parent pom (父依赖)也行，不可配置就很多了，我还不知道怎么配(逃。。。。

    >运行package后看到那个original文件没，那就是原本package应该生成的文件，现在boot做了一个备份以防不测。

	3. 	运行这个jar文件吧   java -jar 生成的文件名.jar  看到输出不动了就是见证奇迹的时刻。。。去浏览器验证吧

     >停止程序，请按ctrl-c

# 三：Spring Boot深入探究

本章前提：快速入门学完，有Spring基础

**问题1：我不想要spring-boot-starter-parent来作为我项目的父pom了**

​	答：行，你依然可以使用scope = import依赖关系来保持依赖关系管理
	也就是搞一个配置

```
<dependencyManagement>
     <dependencies>
        <dependency>
            <!-- Import dependency management from Spring Boot -->
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>1.4.7.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

​	意思是将这个pom文件的依赖关系全部导入项目的pom文件，这样也同样不用写版本号
**问题2： 可我又想指定其中一个版本，怎么办？**
	答：简单，在dependencyManagement里面再声明一个依赖
		但是要在spring-boot-dependencies之前声明。
		比如说

```
<dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
            <scope>import</scope>
 </dependency>
```

​     行了，你再pom写入junit的依赖，默认依赖的就是4.9版本了。

>  由于不使用parent pom ，故其插件也不能用了。

**问题3：什么是启动器**

​	答：启动器其实也是一个依赖，它的`artifactId`名字一般是`spring-boot-starter- *`
		没错，入门程序的spring-boot-starter-web就是一个启动器。
		启动器的作用在于提供了所有Spring和相关技术的一站式服务。
		比如说我们依赖的那个spring-boot-starter-web。就不用自己写一大批的mcv，Spring依赖了。
		该有的，它都帮你依赖好了。
		启动器不止我们快速入门的哪一种，在文档上你还可以找到很多。这里就不赘述了

# 四：使用SpringBoot注意事项

1. 避免使用默认包(没有包名)

2. 把主应用程序放在其他类之上的根包上。

   > 主应用程序，就是包含@EnableAutoConfiguration的注解的类。
   >
   > 其他类的根包上是哪里？比如说有一个dao类放到com.user.dao。
   >
   > 那么根包就是com了。

# 五：SpringBoot的配置

SpringBoot的配置支持两种形式，第一种就是JavaConfig的配置，第二种是基于XML的配置

JavaConfig的配置：
	Spring支持使用配置类的形式来用java类做配置文件
	这种java类的特点在于加了一个注解`@Configuration`
	配置类可以有多个，最后在启动类用`@Import`来导入这些配置类。
	或者`@ComponentScan`自动扫描所有Spring组件，包括@Configuration类。

> @ComponentScan能扫描的Spring组件：@Component, @Service, @Repository, @Controller

XML的配置：
如果还是想使用xml，可以在配置类或者启动类上加上@ImportResource注释来加载XML配置文件.

SpringBoot的配置还有一个特点
       `SpringBoot` 会根据你添加的依赖jar包来自动配置。
	比如说，你没有配置任何数据源，而且依赖里面有个内存数据库。
      `SpringBoot`就会自动配置这个内存数据库作为数据源。
	要启用这个功能，请将 `@EnableAutoConfiguration` or `@SpringBootApplication` 添加到启动类中

排除自动配置引入的配置
	自动配置配置了你讨厌的配置？在配置类为`@EnableAutoConfiguration`添加一个`exclude`属性
	如：`@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})`
	什么，找不到`DataSourceAutoConfiguration`这个类
	要不用`excludeName`属性吧，写上这个类的完全限定名。

# 六：注解的解释

@SpringBootApplication：
	其实包含三种注解：@Configuration，@EnableAutoConfiguration和@ComponentScan 
	由于这三个经常使用.
	所以boot新加了此注解来代表着三种注释并使用默认属性。

# 七：Spring Boot的奇技淫巧

## 7.1 奇技淫巧:驱动工具依赖

Spring Boot有一个依赖

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-devtools</artifactId>
	<optional>true</optional>
</dependency>
```

此依赖有以下功能

1. 禁用Spring Boots支持的几个缓冲库，避免开发出现缓冲问题
2. 自动重启，当你修改了类路径的任何一个文件就会触发
3. 在生产模式会禁用这个开发工具

## 7.2 奇技淫巧:怎么用boot来搞自动注入 

问题的产生：
	有些第三方类库可不允许你加@Component注解，怎么把它们产生的对象交由Spring来管理呢？
	答：两种方式，xml或者java类配置
XML的配置:不讲,实际上和Spring的Bean的配置没什么区别

Java的配置：

1. 创建一个JavaConfig类，参见项目的`com.SpringBoot.Config.SpringBeanConfig`类

 2. 创建一个方法，返回类型即就是bean，方法上面加注解`@Bean`,参见`httpClientFactory`方法

    > 如果这个bean有引用的bean需要手动注入的话，可以参见`restTemplate`方法

# 八：Spring Boot与其他技术交叉的地方

## 8.1 使用Spring Boot 配置一个拦截器

实现步骤：

1. 建立一个拦截器类，实现HandlerInterceptor 
2. 写一个配置类，继承`WebMvcConfigurerAdapter`,此配置类加`@Configure`
3. 根类（boot主要运行的类）加上@SpringBootApplication注解   or   @CompScreen  or  @Import导入配置类	




​		


​	
