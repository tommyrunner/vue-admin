package com.tommy.vueadmin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tommy.vueadmin.dao.UserDao;
import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.TokenEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import com.tommy.vueadmin.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/4
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    UserDao userDao;//用户dao
    @Autowired
    RolesDao rolesDao;
    @Autowired
    RedisTemplate redisTemplate;     //redis k-v操作字符串

    public Map<String, Object> loginUserService(Map<String, Object> userMap) {
        //校验
        if (userMap == null || userMap.get("user") == null || userMap.get("pwd") == null || userMap.get("code") == null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "缺少参数", null);
        }
        String user = (String) userMap.get("user");
        String pwd = (String) userMap.get("pwd");
        String code = (String) userMap.get("code");
        if ("".equals(user) || "".equals(pwd) || "".equals(code)) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "参数不能为空", null);
        }
        UserEntity byUser = userDao.findByUser(user);
        //验证用户是否存在
        if (byUser == null) {
            //用户存在
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户不存在", null);
        }
        //验证密码是否正确
        if (!pwd.equals(byUser.getPwd())) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "密码不正确!", null);
        }
        //去掉不需要显示内容
        byUser.setPwd("*********");
        //获取用户权限
        List<RolesEntity> rolesUsers = rolesDao.findUserIdToRolesEntity(byUser.getId());
        //生成token
        String token = TokenUtil.sign(byUser, rolesUsers);
        //cun
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTime(new Date().getTime());
        tokenEntity.setToken(token);
        redisTemplate.opsForValue().set(byUser.getUser(), JSONObject.toJSONString(tokenEntity));
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "登录成功!", token);
    }

    //通过token获取用户详情
    public Map<String, Object> getUserInfoService(String token) {
        DecodedJWT jwt = TokenUtil.verify(token);
        if (jwt == null) {
            //自动过期时间-token错误/或者过期
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_TOKEN, "身份错误或失效", null);
        } else {
//            System.out.println("issuer: " + jwt.getIssuer());
//            System.out.println("过期时间：      " + jwt.getExpiresAt());
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userInfo", jwt.getClaim("userInfo").asString());
            userInfo.put("roles", jwt.getClaim("roles").asString());
            //---
            UserEntity userEntity = JSONObject.parseObject(jwt.getClaim("userInfo").asString(), UserEntity.class);
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", userInfo);
        }
    }

    //    退出登录
    public Map<String, Object> loginOutService(String token) {
        DecodedJWT jwt = TokenUtil.verify(token);
        if (jwt == null) {
            //自动过期时间-token错误/或者过期
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_TOKEN, "身份错误或失效", null);
        } else {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userInfo", jwt.getClaim("userInfo").asString());
            userInfo.put("roles", jwt.getClaim("roles").asString());
            //---
            UserEntity userEntity = JSONObject.parseObject(jwt.getClaim("userInfo").asString(), UserEntity.class);
            //删除token
            redisTemplate.delete(userEntity.getUser());
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!", userEntity.getUser());
        }
    }
}
