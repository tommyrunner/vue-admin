package com.tommy.vueadmin.controller;

import com.google.code.kaptcha.Producer;
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
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/2
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private Producer producer;  //生成图片验证码
    @Autowired
    UserService userService;    //用户service

    @GetMapping("/code")
    public void loginCode(HttpServletResponse response) {
        log.debug("-------------------loginCode:获取验证码-------------");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //个位数字相加:producer生成5位数,我们只需要取第一位合第二位
        String s1 = text.substring(0, 1);
        String s2 = text.substring(1, 2);
        int count = Integer.parseInt(s1) * Integer.parseInt(s2);
        //生成图片验证码
        BufferedImage image = producer.createImage(s1 + "*" + s2 + "=?");
        //保存 redis key 自己设置
        //stringRedisTemplate.opsForValue().set("",String.valueOf(count));
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户", name = "user", required = true),
            @ApiImplicitParam(value = "用户密码", name = "pwd", required = true),
            @ApiImplicitParam(value = "登录验证码", name = "code", required = true),
    })
    public Map<String,Object> userLogin(
            @ApiParam(value = "接收对象", name = "userMap", required = true)
            @RequestBody(required = false) Map<String, Object> userMap) {
        log.debug("-------------------userLogin:用户登录-------------");
          return   userService.loginUserService(userMap);

    }
    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(String token){
        log.debug("-------------------getUserInfo:获取用户详情-------------");
       return  userService.getUserInfoService(token);
    }

    @GetMapping("/loginOut")
    public Map<String, Object> loginOut(String token){
        log.debug("-------------------loginOut:登出-------------");
        return  userService.loginOutService(token);
    }

}
