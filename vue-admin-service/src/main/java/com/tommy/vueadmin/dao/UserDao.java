package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Tommy
 * 2021/6/4
 */
public interface UserDao extends JpaRepository<UserEntity,Integer> {
    //通过user查询用户
    UserEntity findByUser(String user);
}
