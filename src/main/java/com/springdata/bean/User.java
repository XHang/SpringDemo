package com.springdata.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * User实体类对象，与数据库的User表是对应的
 */
//此注解表示此类是跟数据库有关联的实体类，由于没有加@Table注解，假设数据库的表名就叫User吧
@Entity
public class User {

    /**
     * JPA需要实体类提供一个无参的构造函数
     */
    protected User(){};

    /**
     * 方便程序内创建对象并存储
     * @param userName
     * @param password
     */
    public User(String userName,String password){
        this.password = password;
        this.userName = userName;
    }
    //下面两个注解表示此ID字段是主键字段，该主键为自动生成
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //下面的属性未加注解，就假定字段名也叫如此吧。
    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
