package com;

import com.springdata.bean.User;
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

import java.util.List;

@SpringBootApplication
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
    public CommandLineRunner demo(UserDao repository,UserPagingDao userPagingDao) {
        //lambda表达式，其实就是new了一个CommandLineRunner匿名对象
        return (args) -> {
            dynamicquery(repository);
        };
    }

    /**
     * 保存对象
     * @param repository
     */
    private void saveUser(UserDao repository){
        // save a couple of Users
        repository.save(new User("Jack", "12"));
        repository.save(new User("Chloe", "12"));
        repository.save(new User("Kim", "33"));
        repository.save(new User("David", "44"));
        repository.save(new User("Michelle", "11"));

        for (int i=0;i<100;i++){
            repository.save(new User("张哥第"+i+"儿子", "ignore"));
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

    private void dynamicquery(UserDao dao){
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
}



