package com.tmm.tmmhome.config.TokenConfig;

import com.tmm.tmmhome.config.RequestFilter;
import com.tmm.tmmhome.dao.UserDataDao;
import com.tmm.tmmhome.dao.UserVisitDao;
import com.tmm.tmmhome.emtity.UserVisit;
import com.tmm.tmmhome.service.UserSetServiceImpl;
import com.tmm.tmmhome.service.UserVisitServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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

    private TokenProvider tokenProvider;
    private StringRedisTemplate stringRedisTemplate;

    public TokenFilter(TokenProvider tokenProvider,StringRedisTemplate stringRedisTemplate) {
        this.tokenProvider = tokenProvider;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    //需要过滤的api
    private static final Set<String> NOT_ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/user/deleteUserOpinion", "/user/setUserData"
                    , "/user/deleteUserTings", "/user/addUserTings", "/user/deleteUserImg"
                    , "/user/addUserImg", "/user/addUserApk", "/user/deleteUserApk", "/user/addUserHtml",
                    "/user/deleteUserHtml", "/user/addUserWord", "/user/deleteUserWord",
                    "/user/addTodoList", "/user/deleteTodoList", "/user/updateTodoList", "/user/getVisit"
            )));

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
            String ip = RequestFilter.getIpAddress(request);
            if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer")) {
                token = bearerToken.replace("Bearer", "");
            }
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            boolean allowedPath = NOT_ALLOWED_PATHS.contains(path);

            if (!allowedPath) {
                //如果是排除的url
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                //没携带token
                if (token == null) {
                    request.getRequestDispatcher("/toError?msg='没有找到token哦'").forward(request, response);
                    return;
                }
                //是否挤出token
                String myToken_ip = stringRedisTemplate.opsForValue().get("tommy_ip");
                if(!ip.equals(myToken_ip)){
                    request.getRequestDispatcher("/toError?msg='异地设备登陆,请重新登录!'").forward(request, response);
                    return;
                }
                Authentication authentication = tokenProvider.getAuthentication(token);//解析token
                //让security去校验token/如果失败(失效,没有,抛出500错误)
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } catch (Exception e) {
            log.debug("解析token错误:"+e.toString());
            request.getRequestDispatcher("/toError?msg='解析token失败!'").forward(request, response);
        }

    }
}
