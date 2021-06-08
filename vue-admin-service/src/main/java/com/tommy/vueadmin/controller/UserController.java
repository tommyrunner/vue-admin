package com.tommy.vueadmin.controller;

import com.google.code.kaptcha.Producer;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.service.UserService;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import com.tommy.vueadmin.utils.TokenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Tommy
 * 2021/6/2
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    UserService userService;    //用户service



    @GetMapping("/code")
    public void loginCode(HttpServletResponse response,String uuid) {
        log.debug("-------------------loginCode:获取验证码-------------");
        try {
            BufferedImage bufferedImage = userService.loginCodeService(response, uuid);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/addUser")
    public Map<String, Object> addUser(@RequestBody(required =  false) UserEntity userEntity) {
        log.debug("-------------------PostMapping:添加用户-------------");
        return userService.addUserService(userEntity);
    }

    @PostMapping("/login")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户", name = "user", required = true),
            @ApiImplicitParam(value = "用户密码", name = "pwd", required = true),
            @ApiImplicitParam(value = "登录验证码", name = "code", required = true),
            @ApiImplicitParam(value = "设备id", name = "uuid", required = true),
    })
    public Map<String, Object> userLogin(
            @ApiParam(value = "接收对象", name = "userMap", required = true)
            @RequestBody(required = false) Map<String, Object> userMap) {
        log.debug("-------------------userLogin:用户登录-------------");
        return userService.loginUserService(userMap);

    }

    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(String token) {
        log.debug("-------------------getUserInfo:获取用户详情-------------");
        return userService.getUserInfoService(token);
    }

    @GetMapping("/loginOut")
    public Map<String, Object> loginOut(String token) {
        log.debug("-------------------loginOut:登出-------------");
        return userService.loginOutService(token);
    }

}
