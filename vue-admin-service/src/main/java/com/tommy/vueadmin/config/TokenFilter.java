package com.tommy.vueadmin.config;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tommy.vueadmin.entity.TokenEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.utils.DataBase;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import com.tommy.vueadmin.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @Author Tommy
 * 2020/12/9
 */

@Slf4j
@Component
public class TokenFilter implements Filter {
    @Autowired
    RedisTemplate redisTemplate;     //redis k-v操作字符串

    //需要过滤的api
    private static final Set<String> PASS_ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/login","/v2/api-docs","/user/code")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 这个allow-headers要配为*，这样才能允许所有的请求头 --- update by zxy  in 2018-10-19
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        String bearerToken = request.getHeader("Authorization");
        try {
            String token = null;
            if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.replace("Bearer ", "");
            }
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            boolean allowedPath = PASS_ALLOWED_PATHS.contains(path);
//            验证路径是否需要token
            if (allowedPath) {
                //不需要验证token
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
//            校验token
            if (token == null) {
                //没有携带token
                request.getRequestDispatcher("/toError?msg=未携带身份!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
                return;
            }
            DecodedJWT verify = TokenUtil.verify(token);
            if (verify == null) {
                //没有携带token
                request.getRequestDispatcher("/toError?msg=身份验证验证失败,请重新登录!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
                return;
            }
            //token过期或者token错误
            UserEntity userEntity = JSONObject.parseObject(verify.getClaim("userInfo").asString(), UserEntity.class);
            if (userEntity == null) {
                request.getRequestDispatcher("/toError?msg=身份验证已经过时或身份验证失败,请重新登录!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
                return;
            }
            //检查redis是否存在token
            Object tokenEntityString = redisTemplate.opsForValue().get(userEntity.getUser());
            if (tokenEntityString == null) {
                //未登录token
                request.getRequestDispatcher("/toError?msg=未存在身份,请登录!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
                return;
            }
//            验证token是否超时
            TokenEntity tokenEntity = JSONObject.parseObject(tokenEntityString.toString(), TokenEntity.class);
            //查询是否身份过时
            if (tokenEntity != null && tokenEntity.getTime() + DataBase.TOKEN_EXPIRE_TIME <= new Date().getTime()) {
                //过期
                request.getRequestDispatcher("/toError?msg=身份验证已经过时,请重新登录!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
                //删除过期的token
                redisTemplate.delete(userEntity.getUser());
                return;
            }
            //放行
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            System.out.println(e.toString());
            log.debug("解析token错误:" + e.toString());
            request.getRequestDispatcher("/toError?msg=解析token失败!&code=" + ReturnDateUtil.CODE_ERROR_TOKEN).forward(request, response);
        }

    }
}
