package com.tommy.vueadmin.utils;

import org.springframework.data.domain.Sort;

/**
 * @Author Tommy
 * 2021/6/8
 */
public class DataBase {
    //    token过期时间 30分钟
    public static final long TOKEN_EXPIRE_TIME = 30 * 60 * 1000;
    //超级用户名称
    public static final String ADMIN_USERNAME = "admin@qq.com";

    public static Sort.Direction getSort(String sort){
        switch (sort){
            case "ASC":
                return Sort.Direction.ASC;
            case "DESC":
                return Sort.Direction.DESC;
            default:
                return Sort.Direction.DESC;
        }
    }

}
