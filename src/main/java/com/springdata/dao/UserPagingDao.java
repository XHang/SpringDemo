package com.springdata.dao;

import com.springdata.bean.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 用于分页的用户dao，只是为了演示而已，实际开发没必要单独创建这么一个dao
 */
public interface UserPagingDao extends PagingAndSortingRepository<User,Long> {

    //不用写任何方法，常见的方法都分页好了
}
