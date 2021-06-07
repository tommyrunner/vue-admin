package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author Tommy
 * 2021/6/4
 */
public interface RolesDao extends JpaRepository<RolesEntity,Integer> {

    /**
     * 根据userId查询权限
     * @param userId 用户id
     * @return
     */
    String SQL = "select r.id, r.icon,r.title,r.roles,r.path,r.note from tb_user_roles ur,tb_roles r where ur.roles_id = r.id and ur.user_id = :userId";
    @Query(value=SQL,nativeQuery=true)
    List<RolesEntity> findUserIdToRolesEntity(@Param("userId") Integer userId);

}
