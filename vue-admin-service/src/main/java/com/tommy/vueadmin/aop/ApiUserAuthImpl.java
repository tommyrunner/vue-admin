package com.tommy.vueadmin.aop;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import com.tommy.vueadmin.utils.TokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 处理特定的接口需要权限
 * @Author Tommy
 * 2021/6/9
 */
@Configuration
@Aspect
public class ApiUserAuthImpl {

    //接受到所有的注解
    @Around("execution(public * *(..))&&@annotation(com.tommy.vueadmin.aop.ApiUserAuth)")
    public Object interceptor(ProceedingJoinPoint pjp){
        //获取注解中的参数和方法里的参数
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ApiUserAuth annotation = method.getAnnotation(ApiUserAuth.class);
        //获取的参数集合pjp.getArgs():所有注解的方法里的参数
        //clickLock.key()：获取注解上的属性值
        Object[] args = pjp.getArgs();//获取所有参数
        c:for (int i = 0; i < args.length; i++) {
            String token = (String) args[i];
            if (!StringUtils.isEmpty(token) && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");//获取token
                //验证是否是超级管理员
                DecodedJWT jwt = TokenUtil.verify(token);
                if (jwt == null) {
                    //自动过期时间-token错误/或者过期
                    return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_TOKEN, annotation.msg(), null);
                } else {
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("userInfo", jwt.getClaim("userInfo").asString());
                    //---
                    UserEntity userEntity = JSONObject.parseObject(jwt.getClaim("userInfo").asString(), UserEntity.class);
                    //只有admin@qq.com才能获取全部用户
                    if (!annotation.auth().equals(userEntity.getUser())){
                        //没有权限查看
                        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, annotation.msg(), null);
                    }
                    break c;
                }
            }
        }
        //继续执行方法
        try {
            return pjp.proceed();  //继续执行
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("错误");
        }
    }
}
