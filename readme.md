# Spring Data JPA

# 一：简单介绍

1. 项目简介：这是一个Spring Data JPA的示例程序

   Spring Data JPA是一个Spring Data系列的一部分，可以轻松的实现基于JPA的存储库。
   使用Spring Data JPA,可以减少原始JDBC要写的很多样板代码，作为开发人员，只需要编写存储库接口，Spring 将提供自动实现

   > 实际上吧，我觉得就是Spring开发的一个ORM框架，跟Mybatis，Hibernate一样。
   >
   > 所谓JPA，其实就是Java对于持久化API的定义

2. 特性

   1. 基于Spring和JPA提供构建存储库的高级支持

   2. 支持 [Querydsl]谓词来提供类型安全的JPA查询

   3. 域类的透明审计`Transparent auditing of domain class`

   4. 支持分页，动态查询语句的执行，集成自定义数据访问数据的能力

      > `ability to integrate custom data access code `

   5. 在bootstrap  时验证`@Query` 注解查询

   6. 支持XML的bean映射

   7. 使用`@EnableJpaRepositories`来实现JavaConfig配置

# 二 快速入门

快速入门的目标在于用SpringBoot快速构建一个应用，用Spring Data JPA来访问数据

步骤

1. 往自己的项目的pom文件添加以下依赖

   ```
   <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.0.2.RELEASE</version>
    </parent>
   ```

   目的：在于从`spring-boot-starter-parent`继承过来，添加的依赖大多无需添加版本

   ```
   <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-data-jpa</artifactId>
   </dependency>
   ```

   目的：就是Spring Data JPA的核心包

   ```
   <dependency>
               <groupId>com.h2database</groupId>
               <artifactId>h2</artifactId>
    </dependency>
   ```

   目的：就是h2数据库的依赖，要是想用其他数据库，也可以改成其他数据库的驱动包

   ```
   <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
   </dependency>
   ```

   目的：我们这个程序的目的就是测试Spring Data JPA，所以加这个`spring-boot-starter-test`的启动器

   这个启动器默认就依赖了Junit，Spring-Test的依赖，你无需手动添加

   ```
    <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
   ```

   目的：Spring Boot的插件，可以支持以下Maven命令

      	1. `spring-boot:repackage `
   		2. `spring-boot:run `
   		3. `spring-boot:start `
   		4. `spring-boot:build-info `

2. 构建自己的Dao接口类，必须继承自Repository类或者Repository类的子类

3. 构建启动器。配置一个Bean `CommandLineRunner`，配置方法里面写CURD示例程序

4. 运行主程序，或者`Spring-boot:run`

  > 注：由于依赖项里面默认添加了h2数据库,所以SpringBoot里面默认配置了h2数据库的连接

番外篇：将项目改造为使用Pg数据库来经常存储

1. 添加PG数据库的依赖

   ```
   <dependency>
             <groupId>org.postgresql</groupId>
             <artifactId>postgresql</artifactId>
             <version>42.2.2.jre7</version>
    </dependency>
   ```

2. 添加关于数据库的配置

   在resources文件夹里面创建一个文件`application.properties` ,里面填充一下内容

   ```
   spring.jpa.hibernate.ddl-auto=create
   spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
   spring.datasource.username=postgres
   spring.datasource.password=super
   ```

   然后在原来的基础上运行程序吧，没错，就这么简单

   > 才怪，BUG记录贴
   >
   > 1. 启动时报` Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented`
   >
   >    但是此错误抛出后，程序继续运行。
   >
   >    这是一个JBOSS社区广为人知的BUG,它存在于`Spring-Boot 2.0.0.RC1 `之前或者更高的版本中
   >
   >    需要在`application.properties`文件追加此配置
   >
   >    `spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults= false`
   >
   >    这段配置将禁用加载所有元数据，包括列的注释等等。

  

# 三：JPA规范

由于Spring Data JPA实现了这个JPA规范。那么这个JPA规范，自然是要理解一下的。

JPA全称为`Java Persistence API. `,它通过注解或者XMl把bean和数据库的表关联起来，并可以将这个bean保存到数据库中。

只要熟悉了JPA规范，那么所有实现这个JPA规范的ORM框架就可以很快上手了。
使用这个规范的项目，也可以替换基于JPA规范的其他ORM框架了。

这就是它的好处。

JPA规范的特点

1. 标准化，它是一个持久化框架的API接口，任何基于JPA规范的ORM框架都要实现它

2. 支持大数据，并发，事务

3. 简单易用，只需要几个注解就可以创建一个实体

4. 查询语句是面向对象的，而不是面向数据库的。极少需要手写sql类似的语句。

   就算有，也不是sql，而是JPQL的语句，面向对象的语句。

   > 其实很像Hibernate了，不过写sql还是不太好。。。

## 3.1 JPA规范的注解一览

| **注解**           | **描述**                                                     |
| ------------------ | ------------------------------------------------------------ |
| @Entity            | 声明类为实体或表。                                           |
| @Table             | 声明表名。                                                   |
| @Basic             | 指定非约束明确的各个字段。                                   |
| @Embedded          | 指定类或它的值是一个可嵌入的类的实例的实体的属性。           |
| @Id                | 指定的类的属性，用于识别（一个表中的主键）。                 |
| @GeneratedValue    | 指定如何标识属性可以被初始化，例如自动，手动，或从序列表中获得的值。 |
| @Transient         | 指定的属性，它是不持久的，即，该值永远不会存储在数据库中。   |
| @Column            | 指定持久属性栏属性。                                         |
| @SequenceGenerator | 指定在@GeneratedValue注解中指定的属性的值。它创建了一个序列。 |
| @TableGenerator    | 指定在@GeneratedValue批注指定属性的值发生器。它创造了的值生成的表。 |
| @AccessType        | 这种类型的注释用于设置访问类型。如果设置@AccessType（FIELD），然后进入FIELD明智的。如果设置@AccessType（PROPERTY），然后进入属性发生明智的。 |
| @JoinColumn        | 指定一个实体组织或实体的集合。这是用在多对一和一对多关联。   |
| @UniqueConstraint  | 指定的字段和用于主要或辅助表的唯一约束。                     |
| @ColumnResult      | 参考使用select子句的SQL查询中的列名。                        |
| @ManyToMany        | 定义了连接表之间的多对多一对多的关系。                       |
| @ManyToOne         | 定义了连接表之间的多对一的关系。                             |
| @OneToMany         | 定义了连接表之间存在一个一对多的关系。                       |
| @OneToOne          | 定义了连接表之间有一个一对一的关系。                         |
| @NamedQueries      | 指定命名查询的列表。                                         |
| @NamedQuery        | 指定使用静态名称的查询。                                     |

   # 四：Spring Data JPA配置

一般`Spring Data JPA`的配置都是写在resources文件夹里面的`application.properties`

有用的配置如下

| 配置代码                 | 作用    |
| ------------------------ | ------- |
| spring.jpa.show-sql=true | 打印sql |

# 五：Spring data jpa的一些问题

当Spring data jpa 查询出一个对象后，直接将整个对象返回到前端的话，有可能会报一个Josn序列化错误。

如果查询的对象关联了N多的其他对象的话，出现问题的可能性会更大。

目前在该项目中，还未重现这个BUG

# 六： Spring data jpa 的动态查询







# 七： Spring data  jpa 的事务处理

在Spring data jpa中，默认的CURD方法都是事务性的。当然你自己写的方法是没有事务的，你可以有两种选择

1. 在接口方法上加上` @Transactional`

2. 在外部调用的service方法上面写上`  @Transactional`注解，不过这需要你加上下面的配置

   `<tx：annotation-driven />`或显式使用`@EnableTransactionManagement `

3. 在接口上写`@Transactional(readOnly = true)`

   > 一般说来，里面的方法就都是只读查询了，如果里面还是有修改的操作的话，可以选择覆写
   >
   > @` @Transactional`  再来一个@Modifying注解

4. 可以手动使用编程来执行事务，关于这个的其他信息，可以参考本项目里面的`streamQuery`的方法



## 八： Spring data jpa 的审计

不知道用什么词来形容它，它可以在保存实体时，帮你填充保存日期或者是更新日期，更新人，创建人等等。

先挖坑，了解确实有这么一个东西。

# 九：异步查询

就不用解释是什么了吧，就是异步查询











   

   