package com.tommy.vueadmin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.UserEntity;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TokenUtil {

    private static final long EXPIRE_TIME= 15*60*1000;
    private static final String TOKEN_SECRET="tmm-tommy";  //密钥盐

    /**
     * jwt签名
     * @param userInfo 用户详情
     * @return
     */
    public static String sign(UserEntity userInfo, List<RolesEntity> rolesEntities){

        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userInfo", JSON.toJSONString(userInfo))
                    .withClaim("roles",JSON.toJSONString(rolesEntities))
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;

    }


    /**
     * 签名验证
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
           return verifier.verify(token);
        } catch (Exception e){
            //过期/或者token错误验证
            return null;
        }

    }
}