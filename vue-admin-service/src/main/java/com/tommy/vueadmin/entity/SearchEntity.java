package com.tommy.vueadmin.entity;

import lombok.Data;

import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/23
 */
@Data
public class SearchEntity<T> {
    //当前页
    Integer page = 1;
    // 一页几条
    Integer pageSize = 10;
    //升降
    String sort = "DESC";
    //按字段
    String sortKey = "id";
    //模糊查询
    T containing = null;
}
