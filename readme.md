# Spring Data JPA

# 一：简单介绍

1. 项目简介：这是一个Spring Data JPA的示例程序

   Spring Data JPA是一个Spring Data系列的一部分，可以轻松的实现基于JPA的存储库。
   使用Spring Data JPA,可以减少原始JDBC要写的很多样板代码，作为开发人员，只需要编写存储库接口，Spring 将提供自动实现

   > 实际上吧，我觉得就是Spring开发的一个ORM框架，跟Mybatis，Hibernate一样。
   >
   > 所谓JPA，其实就是Java对于持久化API的定义

   该项目包含

   1. 使用悲观锁和乐观锁实现表序列递增操作

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
   	​	2. `spring-boot:run `
   	​	3. `spring-boot:start `
   	​	4. `spring-boot:build-info `

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
|logging.level.org.hibernate.SQL=DEBUG|打印sql|
|logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE|打印sql参数|

顺便日志文件加上这个  
```
	<Logger level="WARN" name="org.hibernate.type.descriptor.sql.BasicExtractor" additivity="false">
			<AppenderRef ref="Console" />
		</Logger>
```
不然日志会刷到你怀疑人生

顺带一提，JDBCTemplate的打印sql语句的配置在此

```
<!-- JDBC 打印sql -->
<Logger level="trace" name="org.springframework.jdbc.core" additivity="false">
			<AppenderRef ref="Console" />
</Logger>
```

put into log4j.xml


# 五：Spring data jpa的一些问题

当Spring data jpa 查询出一个对象后，直接将整个对象返回到前端的话，有可能会报一个Josn序列化错误。

如果查询的对象关联了N多的其他对象的话，出现问题的可能性会更大。

目前在该项目中，还未重现这个BUG

# 六： Spring data jpa 的动态查询

有关动态查询的代码和解释，都放在code里面

但是关于子查询的，今天偸个懒，就先记在这里了

先亮代码

```
  CriteriaBuilder criteriaBuilder  =  entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Subquery subquery = criteriaQuery.subquery(User.class);
        Root<User> root = subquery.from(User.class);
        Join<User,Role> join =  root.join("roles");
        Join<User,Org> join1 = root.join("org");
        Predicate predicate2 = 、、、
        Predicate predicate3 =、、、
        subquery.where(predicate2,predicate3);
        subquery.select(root.get("id"));
		//子查询查根据条件查用户表，拿到用户ID

        Root<User> root1 = criteriaQuery.from(User.class);
        criteriaQuery.select(root1);
        Predicate predicate1 = 、、、
        Predicate predicate5 = 、、、
        //主查询的id不在子查询的id内
        Predicate predicate4 = criteriaBuilder.not(root1.get("id").in((subquery)));
        criteriaQuery.where(predicate1,predicate4,predicate5);
       TypedQuery<User> query =  entityManager.createQuery(criteriaQuery);
       return  query.getResultList();
       
```

以上。





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

# 八： Spring data jpa 的审计

不知道用什么词来形容它，它可以在保存实体时，帮你填充保存日期或者是更新日期，更新人，创建人等等。

先挖坑，了解确实有这么一个东西。

# 九：查询

一个Orm框架，最重要的是什么？当然就是怎么进行增删改查操作，其中查的操作最由重要，幸运的是，

Spring data jpa 为我们提供了多种查询的方式，以下节将介绍Spring data jpa的几种查询方式

## 9.1 从方法派生查询

这个就不用多说了，只要你的查询方法名起的规范一点，Spring data jpa就可以从方法名生成查询语句，查询对象出来，具体有哪些命名规范，可以参考下面备注的表格信息



## 9.x 自定义查询

就不用解释是什么了吧，就是异步查询



## 9.3 动态查询

Spring Data jpa 遵循JPA规范，JPA规范有的东西，Spring Data jpa当然也有了。

其中就包含`Criteria API` 了

Spring Data jpa支持两种不同`Criteria API`的实现，

第一种是用原始的EntityManager来实现。虽然要写很多的样板代码，但是可以书写复杂的查询。

第二种用你的Repository类继承自`JpaSpecificationExecutor`类，你的repository类就有了几个findAll的方法

老实说吧，Spring Data Jpa 提供的`Criteria APi`虽然减少了样板代码的书写，但其实它只能支持where级别的查询，并且只能查询当前的实体类。

为什么这么说，你看到`toPredicate`返回值是什么，`Predicate`对象，这个是一个谓词，表达的意思类似于x!=y

Spring data jpa拿来`Predicate`对象是塞在CriteriaQuery的where里面的。

> 其实官网也隐晦的承认了。。
>
> By writing a `criteria`, you define the where clause of a query for a domain class 
>
> 其实是光明正大的承认的

什么，你想group by ，什么，你想count，这是行不通的，至少我看来不行。

`Criteria API`包含三个几个核心类

`CriteriaQuery`你就把它看做是整个sql语句行了，它上面有N多方法可以用，像什么groupby 啊，where 啊，select 啊

​	调用这些方法就相当于在sql语句上写where，group by什么的。

`CriteriaBuilder` 这个是一个构建器，可以构建一些表达式（`Expression`）或者条件（大于小于什么的）

甚至是sql的方法，都可以构造。

构建出来的东西都是塞到CriteriaQuery里面的。

其实`Criteria api`的东西大致就是这样了，更多内容，还可以看看我写的例子。





# 第十节：Repository自定义实现

Spring data jpa的存储库自定义实现有几种方法

1. 定义一个存储库接口，然后用实现它，创建一个子类处理，这个子类无需使用 Jpa的任何功能，实际上，你要乐意，用JDBC来操作都是可以的。



第六节： Spring data jpa 的其他知识点

1. 从 Aggregate Roots发布事件，实际上，就是说，可以在执行某些存储库操作时触发执行某个方法。



# 第十一节： Spring data jpa的扩展

## 1. Querydsl的扩展

`Querydsl`这个是一个框架，可以构建静态类型的sql查询



# 第十二节：保存实体

一般来说，保存一个实体的方式是调用Spring data jpa已经提供好的save方法进行保存的。

但其实，不只是简单的保存，Spring data jpa会根据对象的不同状态，来决定应该插入一条新纪录，还是说更新记录。对象哪些状态是保存，哪些状态是更新呢？

1. 默认情况下，是根据对象的主键字段来检查的，如果对象的主键字段是null的，save对象就执行插入操作。

   否则，就是更新操作。

2. 当然你可以自定义实现，让你的域类(实体类)去实现Persistable ，覆盖里面的`isNew`方法。

   然后你知道怎么做吧。。

3. 其他少用，不讲

# 第十三节 分离实体

为什么要分离实体，分离实体的作用是什么

要知道，Spring Data JPA 是对HIbernate做了一层封装，底层的实现，用的还是Hibernate

Hibernate对于实体的状态，有几个定义

1. 瞬态

   处于该状态的实体，不会和数据库做关联，就是一个简单的，普通的实体类对象。

   一般直接new出来的对象就处于该状态

2. 持久的

   处于该状态下的实体，对其作出的任何改变，都将会改变到数据库。

3. 游离态

   该对象的Session已经关闭，则该对象就处于游离态。在该状态下，对对象的任何改变都不会影响到数据库

分离实体的作用，就是将持久化对象，变成游离态，这个时候，无论你怎么调戏这个对象，它都不会联系到数据库。
之所以要强调这个功能，是因为今天遇到的一个BUG

具体的操作是，先load出对象。然后将该对象的ID置为0，最后重新save

希望借此插入一条相同的记录。

但是最后却是在一个不相干的代码，报了一个

`identifier of an instance ...... altered from 4 to null`

猜测是持久态的对象，不能更改标识符（ID）

于是通过entityManage.detach(obj);方法。将对象从持久化状态改为游离态。最后解决









# 第十节：JPA 实体继承机制

这一节主要是将实体类之间的继承

## 12.1 使用抽象类继承

业务场景

1. 现在数据库有两张表，两张表里面有很多个字段都是一样的。现在你要为这两张表构建实体类。

   一般的笨方法就是两个实体类独立开来。

   高级点的做法，就是将两个表相同的字段，抽取出来，放在抽象类里面。

   然后分别建子类，放独有的字段。

   所以步骤是这样的

   1. 建立抽象类，抽象类要加`@MappedSuperclass`注解

   2. 建立子类，去继承抽象类，子类只需要写独有的字段就行

      子类除了继承，跟实体类并无任何区别

      完毕

   有几点注意的

   1. 抽象类实际并不关联到任何一张表，所以不要给他加@Entity和@Table注解
   2. 抽象类的字段跟实体类的字段声明完全一致



# 第十三节 JPA关联关系

## 13.1 一个表对应多个实体类

业务场景：有一个字典表，专门存储各种枚举，字典。

要求要根据枚举的类别，用实体类一一对应。

再详细说明，就是有一个字典表，里面水表类型和状态。

但是对应到项目中，是两个实体类。

这个怎么实现？

要通过两个注解

```
@DiscriminatorColumn(name = "字段名")
@DiscriminatorValue("字段值")
```

在字典表中，肯定要用一个字段来区分不同的字段。

所以，为你的字典实体类补充这两个注解，足矣



# 13.2 使用非主键关联其他表

之前的关联的模式是

假设表A如此

| 字段名 | 解释        |      |
| ------ | ----------- | ---- |
| id     | 主键        |      |
| b_id   | 关联B的主键 |      |
| name   | 名称        |      |



假设表B如此



| 字段名 | 解释 |      |
| ------ | ---- | ---- |
| id     | 主键 |      |
| name   | 名称 |      |
|        |      |      |

在JPA的Entity上，关联关系就是如此

```
public class A{
    private Integer id;
    private String name;
    @JoinColumn(name = "b_id")
    @OneToOne
    private B b；
}
```

默认JoinColumn 就是关联主键的

但是如果表结构是这样的呢？

假设表A如此

| 字段名 | 解释            |      |
| ------ | --------------- | ---- |
| id     | 主键            |      |
| b_name | 关联B的name字段 |      |
| name   | 名称            |      |

假设表B如此

| 字段名 | 解释 |      |
| ------ | ---- | ---- |
| id     | 主键 |      |
| name   | 名称 |      |

那么JoinColumn就需要补充属性，来告知JPA，A表这个字段，关联的是B表的哪个字段?

```
public class A{
    private Integer id;
    private String name;
    @JoinColumn(name = "b_id",referencedColumnName = "name")
    @OneToOne
    private B b；
}
```

值得一提的是，使用referencedColumnName的关联类，（本例子为B类）

必须实现`Serialization`

# 备注



| key                 | 示例                                                         | JPQL代码段                                           |
| ------------------- | ------------------------------------------------------------ | ---------------------------------------------------- |
| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`       |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`        |
| `Is,Equals`         | `findByFirstname`，`findByFirstnameIs`，`findByFirstnameEquals` | `… where x.firstname = ?1`                           |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`              |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                 |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                 |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                           |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                           |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                              |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                             |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                        |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                    |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`（附加参数绑定`%`）     |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`（与前置绑定的参数`%`） |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`（参数绑定包装`%`）     |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`        |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                           |
| `In`                | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                |
| `NotIn`             | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                            |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                            |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                           |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`              |

# 第十四节：BUG

## 14.1 没有保存成功的BUG

### 14.11 BUG描述

第一次运行update sql 事务结束时，数据没有插入到数据库中

第二次运行update sql 事务结束时，数据却插入到数据库了

### 14.12 BUG追踪

通过一步步调试，确认事务提交的代码是

`TransactionAspectSupport`类的`invokeWithinTransaction方法`

该方法调用了`commitTransactionAfterReturning`执行完毕后，数据插入到数据库了

再进行调试

发现在`JdbcResourceLocalTransactionCoordinatorImpl`的` commit() `方法

调用`JdbcResourceLocalTransactionCoordinatorImpl.this.beforeCompletionCallback();`

即保存

### 14.13 最后结果

唔，怎么说呢？之前查的底层细节一个都没用上。

真正原因是程序在执行这个表的update之前，还执行了一次update.

也就是说，一个事务内，对同一张表，执行了两次update操作。

前一次update字段为空，使用的是JPA的Entity Persistence 方法

后一个update字段为非空，使用的是JDBC的update方法

虽然说，后者的update应该会覆盖前者的update，理应不会报出如此BUG

但实际运行时，最前面到update sql 反而是最后打印的。

所以导致先update的，会覆盖后面的update语句。

对于这个原因，我也有所猜测，大概，是，JPA的Persistence 不会立即生效。

实际也有一个saveAndFlush方法，宣称是立即生效的。

言归正传，最后的解决办法是，前一个的update语句，不要update全部字段。

以上

# 第十五节 ：JPA 监听器的使用

## 15.1 监听器的定义和应用

没什么好说明的，监听器就是监听JPA实体的更新、保存、加载、移除的事件。

可以在这些事件发生时，码一些监听代码，在这些事件触发前，触发后执行。

常用的用处：

1. 更新实体类前，填充实体类的更新时间，更新人
2. 加载实体类前，填充一些字段上去
3. 等等

## 15.2 使用

1. 弄一个监听器类

   ```
   public class EntityListener {
       @PreUpdate
       public void preUpdate(Entity entity){
          
       } 
   }
   
   ```

   其中

   `PreUpdate`的含义就是在更新前触发该方法

   这个注解的其他兄弟姐妹还有

   `PrePersist`   `Preremove`  等等

2. 将监听器类放在实体类的头顶上

```
@Entity
@EntityListeners(EntityListener.class)
public class Entuity{
    
}
```

3. 搞定

## 15.3 禁忌事项

监听代码内不能使用EntityManage的任何方法以及query方法

用了怎么样？

当你在监听器内调用query方法时，会递归调用监听器的方法

导致Stack Overflow Error

为什么？

我猜测是实体在更新前，处于即将刷新到数据库的状态。

此时任何对数据库的操作，都会引发实体类的数据库更新。

而实体类的数据库更新，又会触发更新前操作。

于是就无限循环了

反正官方也说不要这么做，尽量还是避免这种做法

猜测在query方法弄一个独立的事务去读，估计就不会有问题了



# 十六节：JPA的奇技淫巧

## 16.1 为你的实体类加上三头六臂

这次介绍的是一个强大的注解`@Formula`。

该注解可以加在实体类的字段上，而该字段无需在表中存在，它是一个计算字段，通过额外的sql语句，查出来的。

然而它并不可以注释在实体字段上，所以呢？不讲

