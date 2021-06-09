package com.tommy.vueadmin.aop;

import java.lang.annotation.*;

/**
 * @Author Tommy
 * 2021/6/9
 */

@Target({ElementType.METHOD})//扫描方法
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiUserAuth {
    //直接获取参数中的token
    //如果没有权限,需要提示的消息
    String msg() default "身份丢失!";
    //需要指定的权限:默认是admin@qq.com
    String auth() default "admin@qq.com";
}
