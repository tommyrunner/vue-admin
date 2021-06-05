package com.tommy.vueadmin.service;

import com.tommy.vueadmin.dao.UserDao;
import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/4
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;//用户dao
    @Autowired
    RolesDao rolesDao;
    public Map<String,Object> loginUserService(Map<String,Object> userMap){
        //校验
        if(userMap==null||userMap.get("user")==null||userMap.get("pwd")==null||userMap.get("code")==null){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA,"缺少参数",null);
        }
        String user = (String) userMap.get("user");
        String pwd = (String) userMap.get("pwd");
        String code = (String) userMap.get("code");
        if("".equals(user)||"".equals(pwd)||"".equals(code)){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA,"参数不能为空",null);
        }
        UserEntity byUser = userDao.findByUser(user);
        //验证用户是否存在
        if(byUser==null){
            //用户存在
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR,"用户不存在",null);
        }
        //验证密码是否正确
        if(!pwd.equals(byUser.getPwd())){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR,"密码不正确!",null);
        }
        //去掉不需要显示内容
        byUser.setPwd("*********");
        //获取用户权限
        List<RolesEntity> rolesUsers= rolesDao.findUserIdToRolesEntity(byUser.getId());
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("userInfo",byUser);
        returnMap.put("roles",rolesUsers);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK,"登录成功!",returnMap);
    }

}
