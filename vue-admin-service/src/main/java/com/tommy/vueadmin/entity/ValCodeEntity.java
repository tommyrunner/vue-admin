package com.tommy.vueadmin.entity;

import lombok.Data;

/**
 * @Author Tommy
 * 2021/6/2
 */
@Data
public class ValCodeEntity {
    String code;    //码
    long createTime;    //生成时间戳
    String uuid;    //设备id
    String note;    //备注
}
