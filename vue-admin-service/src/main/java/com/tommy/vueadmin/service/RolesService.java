package com.tommy.vueadmin.service;

import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.dao.UserRolesDao;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.UserRolesEntity;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

//    根据用户ui查询权限
    public Map<String,Object> getRolesByUserIdAllService(int userId){
       return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"获取成功!",rolesDao.findRolesByUserId(userId));
    }
    //根据用户批量添加权限
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
        //先全部删除用户权限
        userRolesDao.deleteAllByUserId(userId);
        //批量添加
        List<UserRolesEntity> saveAllUserRoles = userRolesDao.saveAll(userRolesEntities);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"操作成功!",saveAllUserRoles.size());
    }
}
