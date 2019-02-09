package com.springdata.projection;

import org.springframework.beans.factory.annotation.Value;

/**
 * 用户对象的投影
 */
public interface UserDTO {
    //只暴露用户对象的用户名和密码属性，其下面既是用户对象此两属性的get方法
     String getUserName();
     String getPassword();

    @Value("#{target.userName + '_' + target.password}")
     String getUserNameAndPassword();

}
