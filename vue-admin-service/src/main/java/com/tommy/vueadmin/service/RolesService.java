package com.tommy.vueadmin.service;

import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/10
 */
@Service
public class RolesService {

    @Autowired
    RolesDao rolesDao;

//    根据用户ui查询权限
    public Map<String,Object> getRolesByUserIdAllService(int userId){
        if()
       return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"获取成功!",rolesDao.findUserIdToRolesEntity(userId));
    }
}
