package com.tmm.tmmhome.controller;

import com.tmm.tmmhome.utils.OssManagerUtil;
import com.tmm.tmmhome.utils.ReturnDateUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/1/13
 */
@ResponseBody
@Controller
@Slf4j
public class ErrorController {

    @Autowired
    OssManagerUtil ossManagerUtil;

    @ApiOperation("toError-返回错误")
    @RequestMapping("/toError")
    public Map toError(String msg) {
        log.info("-------------------错误返回-------------");
        if(msg==null) msg = "服务器错误!";
        int code = msg.contains("token")?ReturnDateUtil.CODE_ERROR_TOKEN:ReturnDateUtil.CODE_ERROR;
        return ReturnDateUtil.returnData(code, msg, null);
    }
}
