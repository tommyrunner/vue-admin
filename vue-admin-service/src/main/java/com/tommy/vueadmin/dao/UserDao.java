package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/4
 */
public interface UserDao extends JpaRepository<UserEntity,Integer> {
    //通过user查询用户
    UserEntity findByUser(String user);
    //批量查询用户
    List<UserEntity> findUserEntitiesByIdIn(List<Integer> ids);
    //多个键模糊查询
    List<UserEntity> findAllByUserContainingAndNameContainingAndIsDel(String user, String name, Pageable p,boolean isDel);
    //查询未删除的总数
    String SQL1 = "SELECT COUNT(*) FROM tb_user u where u.is_del = 0";
    @Query(value = SQL1,nativeQuery=true)
    Integer userCount();
}
