# Spring IOC 

# 一：简介

项目简介：这个项目是演示Spring的IOC的实例程序

包含几种类型的注入
1. 普通的bean注入

2. 构造器注入

3. set/get方法注入<br/

4. 参数是list集合的<br/

5. 参数是Map集合的<br/>

	. 工厂方法的注入	
 >  TODO：没想到构造器注入还需有一个默认的构造器



# 二：用法



## 2.1 根据条件选择注入

有一个类上面的注解

`@ConditionalOnProperty（value = {"properties.name"},havingValue = "true`)

就是说，如果属性配置文件里面有一个`properties.name`的属性，且值为true.

则该类将会被注入，否则不予注入。

> 另外，除了ConditionalOnProperty注解外，还有其他注解提供了其他方式来限定Bean的注入
>
> 注解名大多以Conditional开头，可以自由选择哦



