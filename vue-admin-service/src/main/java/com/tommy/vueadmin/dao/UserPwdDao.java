package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.UserPwdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Tommy
 * 2021/6/15
 */
public interface UserPwdDao extends JpaRepository<UserPwdEntity,Integer> {
    //根据用户id查询
    UserPwdEntity findByUserId(int userId);
}
