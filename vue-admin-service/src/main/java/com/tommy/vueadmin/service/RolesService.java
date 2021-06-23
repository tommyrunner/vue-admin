package com.tommy.vueadmin.service;

import com.tommy.vueadmin.aop.ApiUserAuth;
import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.dao.UserDao;
import com.tommy.vueadmin.dao.UserRolesDao;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.entity.UserPwdEntity;
import com.tommy.vueadmin.entity.UserRolesEntity;
import com.tommy.vueadmin.utils.DataBase;
import com.tommy.vueadmin.utils.MyUtils;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * @Author Tommy
 * 2021/6/10
 */
@Service
public class RolesService {

    @Autowired
    RolesDao rolesDao;
    @Autowired
    UserRolesDao userRolesDao;
    @Autowired
    UserDao userDao;

//    根据菜单ui查询菜单
    public Map<String,Object> getRolesByUserIdAllService(int userId){
       return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"获取成功!",rolesDao.findRolesByUserId(userId));
    }
    //根据菜单批量添加菜单
    @Transactional
    public Map<String,Object> saveRolesByUserService(Map<String,Object> map){
        //校验
        if (map == null || map.get("userId") == null || map.get("rolesAll") == null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "缺少参数", null);
        }
        Integer userId = (Integer) map.get("userId");
        List<String> rolesAll = ( List<String>) map.get("rolesAll");
        //批量添加到表中
        List<RolesEntity> rolesEntitiesByRolesIn = rolesDao.findRolesEntitiesByRolesIn(rolesAll);
        List<UserRolesEntity> userRolesEntities = new ArrayList<>();
        for(RolesEntity item : rolesEntitiesByRolesIn){
            UserRolesEntity userRolesEntity = new UserRolesEntity();
            userRolesEntity.setUserId(userId);
            userRolesEntity.setRolesId(item.getId());
            userRolesEntity.setNote("");
            userRolesEntities.add(userRolesEntity);
        }
        //先全部删除菜单菜单
        userRolesDao.deleteAllByUserId(userId);
        //批量添加
        List<UserRolesEntity> saveAllUserRoles = userRolesDao.saveAll(userRolesEntities);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"操作成功!",saveAllUserRoles.size());
    }
    //查询+模糊
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> getRolesAllService(String token, Map<String,Object> map) {
        //获取相应参数
        Integer page = (Integer) map.getOrDefault("page",1);
        Integer pageSize = (Integer) map.getOrDefault("pageSize",10);
        String sort = (String) map.getOrDefault("sort","DESC");
        String sortKey = (String) map.getOrDefault("sortKey","id");
        String roles = (String) map.getOrDefault("roles","");
        String title = (String) map.getOrDefault("title","");
        String icon = (String) map.getOrDefault("icon","");
        String path = (String) map.getOrDefault("path","");
        String note = (String) map.getOrDefault("note","");
        Sort sortObj = Sort.by(MyUtils.getSort(sort), sortKey);
        Pageable pageable = PageRequest.of(page-1, pageSize,sortObj);
        List<RolesEntity> allContains = rolesDao.findAllByRolesContainingAndTitleContainingAndIconContainingAndPathContainingAndNoteContaining(roles, title, icon, path,note, pageable);
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("size",rolesDao.countAllByRolesContainingAndTitleContainingAndIconContainingAndPathContainingAndNoteContaining(roles, title, icon, path,note));
        returnMap.put("list",allContains);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", returnMap);
    }
    //批量删除
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    @Transactional
    public Map<String, Object> deleteRolesService(String token, List<Integer> rolesId) {
         rolesDao.deleteRolesEntitiesByIdIn(rolesId);
//        //删除关于菜单的权限
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "删除成功!", rolesId.size());
    }
    //保存-修改
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> saveRolesService(String token, RolesEntity rolesEntity) {
        //校验
        RolesEntity byRoles = rolesDao.findByRoles(rolesEntity.getRoles());
        //判断是否添加还是修改
        boolean isAdd = rolesEntity.getId() == 0;
        //如果是添加
        if (isAdd) {
            //判断是否已经存在
            if (byRoles != null) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "菜单已经存在", null);
            }
        } else {
            //判断是否已经存在
            if (byRoles == null) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR,  "菜单不存在!", null);
            }
        }
        RolesEntity save = rolesDao.save(rolesEntity);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, isAdd ? "添加成功!" : "修改成功!", save.getRoles());
    }
    //同步菜单
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    @Transactional
    public Map<String, Object> syncRolesService(String token, List<RolesEntity> rolesEntities) {
        List<RolesEntity> all = rolesDao.findAll();
        List<String> rolesNames = new ArrayList<>();
        //去重并添加
        for(int i = rolesEntities.size()-1;i>=0;i--){
            RolesEntity rolesEntity = rolesEntities.get(i);
            rolesNames.add(rolesEntity.getRoles());
            for(RolesEntity item2 : all){
                if(rolesEntity.getRoles().equals(item2.getRoles())){
                    //发现有相同roles
                    rolesEntities.remove(i);
                    break;
                }
            }
        }
        //多个添加
        List<RolesEntity> saves = rolesDao.saveAll(rolesEntities);
        //并且同步超级用户
        UserEntity userEntity = userDao.findById(1).get();
        if(userEntity.getUser().equals(DataBase.ADMIN_USERNAME)){
            Map<String,Object> allMap = new HashMap<>();
            allMap.put("userId",1);
            allMap.put("rolesAll",rolesNames);
            saveRolesByUserService(allMap);
        }
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "同步成功!",saves.size() );
    }
}
