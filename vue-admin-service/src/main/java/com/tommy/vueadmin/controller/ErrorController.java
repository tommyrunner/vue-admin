package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.utils.ReturnDateUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author Tommy
 * 2021/1/13
 */
@RestController
@Slf4j
public class ErrorController {


    @ApiOperation("toError-返回错误")
    @RequestMapping("/toError")
    public Map<String,Object> toError(String msg,int code) {
        log.debug("-------------------错误返回-------------");
        if(msg==null) msg = "服务器错误!";
        return ReturnDateUtil.returnData(code, msg, null);
    }
}
