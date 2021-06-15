package com.tommy.vueadmin.utils;

import org.springframework.data.domain.Sort;

/**
 * @Author Tommy
 * 2021/6/13
 */
public class MyUtils {
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
