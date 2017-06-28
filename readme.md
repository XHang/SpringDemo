***
SpringBoot环境搭建 
1：新建一个Maven项目
2：在pom文件的project标签下补上
		<parent>
			    <groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-parent</artifactId>
			    <version>1.5.4.RELEASE</version>
		</parent>
3：引入此依赖
	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
4：新建两个类文件，一个作为控制器类，一个作为启动类
		控制器类随便写一个普通的映射方法即可，能在启动后通过浏览器访问到即可,对了，还要在类上补上@EnableAutoConfiguration注解
		启动类吗。。。加个主函数即可
		运行这个代码：SpringApplication.run(控制器类名.class);
		看到控制台输出：initialization completed in 17 ms
		看看 Tomcat initialized with port(s): 8080 (http)端口
		接下来应该知道怎么做了吧？
		访问你的控制器方法，走起。
		恭喜，环境搭建完毕
***

***
现在开始解释pom文件的几个要点
1：为什么要搞一个parent标签？
	其实是为了入门程序简单，搞一个parent可以使你的项目继承于这个。
	想要添加什么依赖都不用考虑版本冲突。
	亲测，将parent标签去掉，指定依赖的版本号，项目仍可以启动。这也就       是说parent不提供依赖关系（废话）
	版本要选好啊
	以及
	让你的项目依赖这个parent，能获得合适的默认值，包括
	1：  Java 1.6作为默认编译器级别。覆写这个属性轻松改变jdk编译版本
		<properties>
    		<java.version>1.8</java.version>
		</properties>
    2：  UTF-8源代码编码。
    3：填写父pom已有的依赖时不用加version，避免版本冲突
    4：聪明的资源过滤？   不理解
    5：聪明的插件配置 
    		最后一点：由于默认配置文件接受Spring样式占位符（$ 			{...}），Maven过滤更改为使用@ .. @占位符
    		（您可以使用Maven属性resource.delimiter覆盖它）。不理解
    另加：boot提供了一个属性可以让你选择依赖的其他版本。比如parent有一个log4j2   2.7的版本，你觉得不爽，你可以添加
    这个属性
    	<properties>
   			 <log4j2.version>2.9</log4j2.version>
		</properties>
		这样你在项目的依赖中添加一个log4j2,不用指定版本号，它默认就升级到了2.9版本
    		
    		
    		
2：引入的依赖spring-boot-starter-web包括什么？
		包括Tomcat web 服务器和Springboot本身
3：@EnableAutoConfiguration这个注解是什么鬼？
		这个是SpringBoot根据你添加的 j  a  r  包  依赖来猜测这个项目的Spring该如何配置。（自动配置）
		上面pom文件不是加了个spring-boot-starter-web依赖嘛，Springboot注意到了那个web，所以它就猜测你正在开发web程序，顺便把配置给你解决了。
		还要写xml文件吗？不用了，让xml见鬼去吧
		那么，@EnableAutoConfiguration应该写在哪里？
		写在SpringApplication.run(类.class);那个运行的类中。现在看起来，那个类是哪个都可以，哪怕是本类也可。
		不过最好把那个类写在根包中
4：注：@EnableAutoConfiguration自动配置和启动器并不相关，你可以自由选择启动器之外的jar包依赖项，Springboot仍然可以自动配置你的应用程序
		简单的说，就算你使用传统的tomcat---clean tocam7:run来运行服务器。自动配置也不会失效的，放心吧
5：对了，不去掉parent标签的话，你还可以获得一个新技能spring-boot:run，直接运行这个maven命令可以直接启动项目。。。
		然而，毫无卵用。
SpringBoot的黑魔法，用一个jar包运行web应用-----就是不用任何的外部容器，直接在jar包就可以独立运行一个web应用。
		如何开始：
		1：在项目的pom文件添加一个插件配置
			<build>
    			<plugins>
        				<plugin>
					            <groupId>org.springframework.boot</groupId>
					            <artifactId>spring-boot-maven-plugin</artifactId>
        				</plugin>
    			</plugins>
			</build>
		2:运行package命令。查看生成的包，大小应该超过5mb了，如果你看到的只有5kb。。。把上面的parent加上去吧，没他也可
			不过配置就很多了（其实是我还不知道怎么配。（逃。。。）
			注：运行package后看到那个original文件没，那就是原本package应该生成的文件，现在boot做了一个备份以防不测。
		3：运行这个jar文件吧   java -jar 生成的文件名.jar  看到输出不动了就是见证奇迹的时刻。。。去浏览器验证吧。。。
		注：停止程序，请按ctrl-c
开胃小菜结束，下面是SpringBoot进阶
	先决条件：1：快速入门学完，有Spring基础
	
第一部分：我不想要spring-boot-starter-parent来作为我项目的父pom了。
	行，你依然可以使用scope = import依赖关系来保持依赖关系管理
	也就是搞一个配置
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
	意思是将这个pom文件的依赖关系全部导入项目的pom文件，这样也同样不用写版本号
	可我又想指定其中一个版本，怎么办？
	简单，在dependencyManagement里面再声明一个依赖，但是要在spring-boot-dependencies之前声明。
	比如说，
	<dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.9</version>
            <scope>import</scope>
     </dependency>
     行了，你再pom写入junit的依赖，默认依赖的就是4.9版本了。
	注意：以上都不使用	spring-boot-starter-parent这个作为父类pom。其插件也不能用了。
第二部分：启动器
	启动器其实也是一个依赖，它的artifactId名字一般是spring-boot-starter- *
	没错，入门程序的spring-boot-starter-web就是一个启动器。
	启动器的作用在于提供了所有Spring和相关技术的一站式服务。
	比如说我们依赖的那个spring-boot-starter-web。就不用自己写一大批的mcv，Spring依赖了。
	该有的，它都帮你依赖好了。
	启动器不止我们快速入门的哪一种，在文档上你还可以找到很多。这里就不赘述了
第三部分：使用SpringBoot注意事项
1：避免使用默认包
2：把主应用程序放在其他类之上的根包上。
	几个概念：主应用程序，就是包含@EnableAutoConfiguration的注解的类。
	其他类的根包上是哪里？比如说有一个dao类放到com.user.dao。
	那么根包就是com了。

第四部分：SpringBoot的配置
----------------------------
	1.  Spring支持使用配置类的形式来用java类做配置文件，这种java类的特点在于加了一个注解@Configuration，配置类可以有多个，最后用@Import来导入这些配置类。或者@ComponentScan自动配置所有Spring组件，包括@Configuration类。
	2. 如果还是想使用xml，可以在配置类加上@ImportResource注释来加载XML配置文件.
	3:SpringBoot会根据你添加的依赖jar包来自动配置。比如说，你没有配置任何数据源，而且依赖里面有个内存数据库，SpringBoot就会自动配置这个内存数据库作为数据源。要启用这个功能，请将 @EnableAutoConfiguration or @SpringBootApplication 添加到配置类中
	4：貌似，使用debug可以查看当前启用的自动配置是那些？
	5. 自动配置配置了你讨厌的配置？在配置类为@EnableAutoConfiguration添加一个exclude属性
	如：@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
	什么，找不到DataSourceAutoConfiguration这个类
	要不用excludeName属性吧，写上这个类的完全限定名。
	5:@ComponentScan注解加在配置类中可以搜索以下含有该注解的bena
		@Component, @Service, @Repository, @Controller
	6：由于@Configuration，@EnableAutoConfiguration和@ComponentScan 这三个经常使用。
		所以boot新加了一个注解@SpringBootApplication来代表着三种注释并使用默认属性。
	7：boot提供了一个开发工具包
		添加这个依赖以got it!
		<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
    它能帮你：	1.禁用Spring Boots支持的几个缓冲库，避免开发出现缓冲问题
    			2.自动重启，当你修改了类路径的任何一个文件就会触发，
    			3.在生产模式会禁用这个开发工具。
    			注意： 
    			1. DevTools需要一个隔离的应用程序类加载器才能正常运行？
    			2.禁用了关闭挂钩（SpringApplication.setRegisterShutdownHook(false))无法正常启动DevTools
***
第五部分，怎么利用boot搞一个拦截器
------------------

  1.  暂时不提配置，第一步，你应该知道吧，建立一个拦截器类，实现HandlerInterceptor 
  2.  好了，建了一个标准的拦截处理器，怎么配置上去，这才是问题。
  		其实说起来也简单，只要新建一个类去继承WebMvcConfigurerAdapter，就可以写进配置了。
  3.  新建一个配置类，覆盖某个方法，写入自己的拦截器配置，然后呢？怎么使配置生效。
  		简单，两步
  		1. 配置类加上@Configure注解  		
		2. 根类（boot主要运行的类），加上@SpringBootApplication注解   or   @CompScreen  or  @Import导入配置类
		搞定
		
第六部分，怎么用boot来搞自动注入
---------------------------------------------	
话说，有些第三方类库可不允许你加@Component注解，怎么把它们产生的对象交由Spring来管理呢？
两种方式，xml或者java类配置

java类配置
======
1. 新建一个类作为Spring的把bean配置，别忘了加@Configure注解
2. 搞一个方法，返回对象，方法上加上@Bean。这样的做法跟xml配置中的<bean></bean>是一样的。。。。id怎么确定？
	示例程序，注入一个restTemplate服务对象
	
杂项
---------------------
1.  自己启动Spring容器时，可以用ClassPathXmlApplicationContext接受一个@Configuration注解的类，来实例化里面的bean。
	eg:    
	` 
	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
    MyService myService = ctx.getBean(MyService.class);
    myService.doStuff();
    `
						
			
	
		
		
	