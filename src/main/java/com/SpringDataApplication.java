package com;

import com.springdata.bean.Org;
import com.springdata.bean.StatisticsVo;
import com.springdata.bean.User;
import com.springdata.bean.Vo;
import com.springdata.dao.UserDao;
import com.springdata.dao.UserPagingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.jws.soap.SOAPBinding;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableTransactionManagement
public class SpringDataApplication {
    private static final Logger log = LoggerFactory.getLogger(SpringDataApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class);
    }

    /**
     * 应用启动时，默认就会执行这个bean方法注册。
     * @param repository
     * @return
     */
    @Bean
    public CommandLineRunner demo(UserDao repository,UserPagingDao userPagingDao,PlatformTransactionManager pa,EntityManager manager) {
        //lambda表达式，其实就是new了一个CommandLineRunner匿名对象
        return (args) -> {
            springDataJpaDynamitQuery(repository);
        };

    }


    /**
     * 保存对象
     * @param repository
     */
    private void saveUser(UserDao repository){
        for (int i=0;i<100;i++){
            User user= new User("用户"+i, "ignore");
            user.setCreateDate(new Timestamp(new Date().getTime()));
            Org org = new Org();
            org.setOrgCode("orgCode"+i);
            org.setOrgName("组织编码"+i);
            user.setOrg(org);
            repository.save(user);
        }

    }


    private void queryUser(UserDao repository){
        // fetch all Users  取所有用户
        log.info("Users found with findAll():");
        log.info("-------------------------------");
        for (User User : repository.findAll()) {
            log.info(User.toString());
        }
        log.info("");
        // fetch an individual User by ID  通过用户ID取得单独的用户
        repository.findById(1L)
                .ifPresent(User -> {
                    log.info("User found with findById(1L):");
                    log.info("--------------------------------");
                    log.info(User.toString());
                    log.info("");
                });

        // 通过名称取到用户
        log.info("User found with findByLastName('Bauer'):");
        log.info("--------------------------------------------");
        repository.findByUserName("Bauer").forEach(bauer -> {
            log.info(bauer.toString());
        });
        //下面的代码和上面的一样
        // for (User bauer : repository.findByLastName("Bauer")) {
        // 	log.info(bauer.toString());
        // }
    }

    private void queryUserByPaging( UserPagingDao pagingDao){
        Page<User> userPage = pagingDao.findAll(PageRequest.of(1,20 ));
        System.out.println("查询出来的总记录有"+userPage.getTotalElements());
        System.out.println("总共有"+userPage.getTotalPages()+"页");
        System.out.println("当前为第"+userPage.getNumber()+"页");
        System.out.println("本页有"+ userPage.getSize()+"记录数");
        userPage.forEach((args)->System.out.println("当前用户名是"+args.getUserName()));
    }

    private void sliceQuery(UserDao dao){
        saveUser(dao);
        Slice<User> users =dao.findByPassword("ignore",PageRequest.of(0,5));
        System.out.println(users.getNumber());
        //无论调用多少次nextPageable，得到的结果都是一样
        Pageable able = users.nextPageable();
        List<User> list = users.getContent();
        int number = users.getNumber();
        int eleNumber = users.getNumberOfElements();
       int size =  users.getSize();
        return ;
    }

    /**
     * 演示流式查询结果
     * 流式对象，其实是JDK8 lambda里面的一个小东西
     * @param dao
     */
    private void streamQuery(UserDao dao,PlatformTransactionManager platformTransactionManager){
        saveUser(dao);
        //编程式开启事务
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                Stream<User> stream = dao.findByUserNameLike("%用户%");
                stream.forEach((args)->System.out.println("遍历出来的用户名是"+args.getUserName()));
                //stream里面包含了一个底层的资源，不用时需要关闭
                System.out.println("查询结束");
                stream.close();
            }
        });
    }

    /**
     * 根据name字段的开始的前几个字符进行查找
     * @param dao
     */
    public void executeSpecialQuery(UserDao dao){
        saveUser(dao);
        List<User> users = dao.findByUserNameStartingWith("用户");
        users.forEach((user)->System.out.println(user.getUserName()));
    }
    /**
     * 1. multiselect指定了多个字段显示的话，对应的对象就要为其指定对应的构造函数
     * 比如说我里面指定了要select userName和password,那么我对应的实体类就要添加一个userName和password的构造函数
     * 生成的sql语句如下：select userName,password from _user ;
     */
    public  void multiEntityFieldQueryByDymanic(UserDao dao,EntityManager entityManager){
        saveUser(dao);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //创建查询对象
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        //就是指定from 后面的表的名字，是嘛
        Root<User> from =  query.from(User.class);
        //来设置结果类型,multiselect是select多个字段，select方法只select一个字段
        query.multiselect(from.get("userName"),from.get("password"));
        //    q.select(order.get("shippingAddress").<String>get("state"));
        //准备执行查询而创建的TypedQuery对象
        TypedQuery<User> q = entityManager.createQuery(query);
        List<User> list = q.getResultList();
        list.forEach((user)->System.out.println(user));
    }

    /**
     * select count(*),password,'ax' from User group by password
     * 这个例子展现了更有趣的内容，他查询出了一个新的字段值-ax，但是这个字段值不属于实体类的任何字段
     * 这个时候，你要把他保存起来，就要用到DTO，如下文所示，你可以建一个VO类，然后这个查询语句要查询哪个字段。
     * 你就要按顺序把这些字段构造出一个构造器出来。不按顺序？JPA不知道select出来的字段和实体类的字段之间的对应关系
     * @param dao
     * @param entityManager
     */
    public  void multiFieldQueryByDymanic(UserDao dao,EntityManager entityManager){
        saveUser(dao);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        //创建类型安全的查询时，指定查询的对象
        CriteriaQuery<Vo> query = criteriaBuilder.createQuery(Vo.class);
        //就是指定from 后面的表的名字，是嘛
        Root<User> from =  query.from(User.class);
        //就是简单的分组，根据password
        query.groupBy(from.get("password"));
        query.multiselect(criteriaBuilder.count(from),from.get("password"),criteriaBuilder.literal("ax"));
        //准备执行查询而创建的TypedQuery对象
        TypedQuery<Vo> q = entityManager.createQuery(query);
       List<Vo> vos =q.getResultList();
        vos.forEach((vo)->System.out.println(vo.getCount()+"   "+vo.getPassword()));
    }

    /**
     * 演示一下简单的where动态查询
     * 生成的sql语句大致如下：
     * select * from _user where userName = 'Jack'
     */
    public void whereDynamicQuery(EntityManager entityManager,UserDao userDao){
        saveUser(userDao);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery =  criteriaBuilder.createQuery(User.class);
        Root<User> userRooot  = criteriaQuery.from(User.class);
        Predicate whereCondition = criteriaBuilder.equal(userRooot.get("userName"),criteriaBuilder.literal("用户1"));
        criteriaQuery.where(whereCondition);
        criteriaQuery.select(userRooot);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        User user =  typedQuery.getSingleResult();
        System.out.println("查询出来的用户名为"+user.getUserName());
    }

    /**
     * 演示join查询<br/>
     * 一般情况下,只要你的实体类加了OneToOne之类的注解，并且动态查询有把相关联的外键表查出来。<br/>
     * 那么你无需加上join字段，程序自动帮你查出来，厉害吧。<br/>
     * 但是，总有一两个的需求需要join表来实现的，所以呢，年轻人，还是要多学习一下的。<br/>
     * 参考sql select user.userName, user.password, org.orgName from _user as user join org on user.org_id = org.id
     * @param entityManager
     */
    public void joinDynamicQuery(EntityManager entityManager,UserDao userDao){
        saveUser(userDao);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vo> criteriaQuery =  criteriaBuilder.createQuery(Vo.class);
        Root<User> userRooot  = criteriaQuery.from(User.class);
        //join连接可以链式编程，join().join().join()...
        Join join =  userRooot.join("org");
        Predicate whereCondition = criteriaBuilder.equal(userRooot.get("userName"),criteriaBuilder.literal("用户1"));
        criteriaQuery.where(whereCondition);
        criteriaQuery.multiselect(userRooot.get("userName"),userRooot.get("password"),join.get("orgName"));
        TypedQuery<Vo> typedQuery = entityManager.createQuery(criteriaQuery);
        Vo vo =  typedQuery.getSingleResult();
        System.out.println("查询出来的用户名为"+vo.getOrgName());
    }

    /**
     *示例sql：select * from _user where group by DATE_FORMAT( apply_time, '%Y-%m-%d' )
     */
    public  void sqlMethodDynamicQuery(EntityManager entityManager,UserDao userDao){
        saveUser(userDao);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatisticsVo> query = criteriaBuilder.createQuery(StatisticsVo.class);
        Root root  = query.from(User.class);
        query.groupBy(criteriaBuilder.function("DATE_FORMAT",String.class,root.get("createDate"),criteriaBuilder.literal("%Y-%m-%d")));
        //count方法也可以用criteriaBuilder里面的count的方法，上面有例子了
        query.multiselect(root.get("createDate"),criteriaBuilder.function("count",Integer.class,root));
        TypedQuery typedQuery = entityManager.createQuery(query);
        List<StatisticsVo> vo =  typedQuery.getResultList();
        vo.forEach((arg)->System.out.println(arg.getDate()+"日期办理人数是"+arg.getCount()));
    }

    /**
     * 使用Spring Data JPA 实现Criteria API
     * 参考sql: select * from _user where username = '用户1';
     * @param userDao
     */
    public void springDataJpaDynamitQuery(UserDao userDao){
        saveUser(userDao);
        List<User> users = userDao.findAll((root,query,build)->{return build.equal(root.get("userName"),build.literal("用户1"));});
        users.forEach((user)->System.out.println(user.getPassword()));
    }




}



