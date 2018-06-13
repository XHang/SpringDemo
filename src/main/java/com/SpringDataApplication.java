package com;

import com.springdata.bean.User;
import com.springdata.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
    public CommandLineRunner demo(UserDao repository) {
        return (args) -> {
            // save a couple of Users
            repository.save(new User("Jack", "Bauer"));
            repository.save(new User("Chloe", "O'Brian"));
            repository.save(new User("Kim", "Bauer"));
            repository.save(new User("David", "Palmer"));
            repository.save(new User("Michelle", "Dessler"));

            // fetch all Users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User User : repository.findAll()) {
                log.info(User.toString());
            }
            log.info("");

            // fetch an individual User by ID
            repository.findById(1L)
                    .ifPresent(User -> {
                        log.info("User found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(User.toString());
                        log.info("");
                    });

            // fetch Users by last name
            log.info("User found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByUserName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
            // for (User bauer : repository.findByLastName("Bauer")) {
            // 	log.info(bauer.toString());
            // }
            log.info("");
        };
    }
}

