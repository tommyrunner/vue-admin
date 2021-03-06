package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.RolesEntity;
import org.springframework.data.domain.Pageable;
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
    String SQL1 = "select r.id, r.icon,r.title,r.roles,r.path,r.note from tb_user_roles ur,tb_roles r where ur.roles_id = r.id and ur.user_id = :userId";
    @Query(value=SQL1,nativeQuery=true)
    List<RolesEntity> findRolesByUserId(@Param("userId") Integer userId);

    /**
     * 根据userId删除权限
     * @param userId 用户id
     * @return
     */
    String SQL2 = "select r.id, r.icon,r.title,r.roles,r.path,r.note from tb_user_roles ur,tb_roles r where ur.roles_id = r.id and ur.user_id = :userId";
    @Query(value=SQL2,nativeQuery=true)
    List<RolesEntity> deleteRolesByUserId(@Param("userId") Integer userId);

    //批量查询用户权限
    List<RolesEntity> findRolesEntitiesByRolesIn(List<String> roles);

    //单个查询用户权限
    RolesEntity findByRoles(String roles);
    //根据id批量删除
    void deleteRolesEntitiesByIdIn(List<Integer> rolesIds);
    //批量查询+分页+模糊
    List<RolesEntity> findAllByRolesContainingAndTitleContainingAndIconContainingAndPathContainingAndNoteContaining(String roles, String title, String icon, String path,String note, Pageable p);
    //根据条件查询总数
    Integer countAllByRolesContainingAndTitleContainingAndIconContainingAndPathContainingAndNoteContaining(String roles, String title, String icon, String path,String note);
}
