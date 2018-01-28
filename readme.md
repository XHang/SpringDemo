# springsecurity组合mvc使用说明书



 ## 实验步骤

1. 引入相关依赖包

2. 创建一个springsecurity的java配置

3. 仅仅创建一个springsecurity的java配置还不够，项目不知道怎么加载这个配置文件

   所以接下来我们要将该配置注册一下

   1. 创建一个MessageSecurityWebApplicationInitializer类作为项目启动的配置文件

      接下来没什么好做了，启动程序吧。

      啊哈？很奇怪对吧，为什么程序能找到你的包，找到创建的那两个配置类，加载配置？

      其实吧，在本项目中有加一个叫spring-security-samples-javaconfig-messages的依赖

      在这个依赖中，有一个MessageWebApplicationInitializer类，它里面有一个getRootConfigClasses方法里面配置了一个RootConfiguration作为用于配置根应用程序上下文的配置

      再转去RootConfiguration类看下，这个类就配置了@ComponentScan，意思就是扫描该包里面的所有配置类，话说回来，我们之前创建的那两个配置类就在这个包里面，因而自然能扫描到

        >   话说回来，尚且有一事不明，程序是怎么知道MessageWebApplicationInitializer这个类的存在，并加载它的？

   2. 接下来，就是测试下功能正不正常了，实际也没什么需要测试的，就是一个邮箱界面而已

      值得一提的是，我们导入的那个项目中，并没有什么前端页面文件，那是因为所有前端页面文件全部都包含在spring-security-samples-javaconfig-messages依赖中了