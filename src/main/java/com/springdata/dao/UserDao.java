package com.springdata.dao;

import com.springdata.bean.User;
import com.springdata.projection.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * dao类，实现用户对象的一系列数据库操作
 * 只需要定义接口，SpringData在运行时自动生成对应的实现类
 * 继承CrudRepository接口会自动继承里面的CURD方法
 */
public interface UserDao  extends CrudRepository<User,Integer> ,JpaSpecificationExecutor<User> {
    /**
     * 根据姓名查找用户记录
     * @param userName 用户名
     * @return
     */
    //使用规范的方法名，可以让SpringData识别到，自动生成sql语句
    List<User> findByUserName(String userName);

    /**
     * 进行分页查询,这个的使用可以参考之前的queryUserByPaging方法
     * @param userName
     * @param pageable
     * @return
     */
    Page<User> findByUserName(String userName, Pageable pageable);

    /**
     * 片段式查询结果
     * @param passWord
     * @param pageable
     * @return
     */
    Slice findByPassword(String passWord, Pageable pageable);

    /**
     * 流式查询结果
     * @return
     */
    Stream<User> findByUserNameLike(String userName);

    List<User> findByUserNameStartingWith(String userNmae);

    /**
     * 使用SpringData JPa 查询零碎字段的第一个手段，就是使用HSQL
     * @param userName  用户名
     * @return
     */
    @Query(value = "select new com.springdata.bean.User(userName,password) from User where userName=?1")
    public User queryByUserNameUsingHSQL(String userName);
    /**
     * 使用SpringData JPa 查询零碎字段的第二个手段，就是使用投影
     * @param userName  用户名
     * @return
     */
    @Query(value = "select user_name userName,password  from _user where user_name=?1",nativeQuery = true)
    public UserDTO queryByUserNameUsingProjection(String userName);







}
