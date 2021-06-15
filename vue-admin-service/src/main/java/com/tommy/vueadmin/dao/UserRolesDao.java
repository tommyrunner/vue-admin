package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.entity.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author Tommy
 * 2021/6/11
 */
public interface UserRolesDao extends JpaRepository<UserRolesEntity,Integer> {
    //批量删除用户权限
    void deleteUserRolesEntitiesByUserIdIn(List<Integer> userIds);
    //根据用户id删除
    void deleteAllByUserId(int userId);
}
