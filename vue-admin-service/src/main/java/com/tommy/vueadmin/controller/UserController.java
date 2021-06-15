package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
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
    UserService userService;    //用户service


    @GetMapping("/code")
    public void loginCode(HttpServletResponse response, String uuid) {
        log.debug("-------------------loginCode:获取验证码-------------");
        try {
            BufferedImage bufferedImage = userService.loginCodeService(response, uuid);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bufferedImage, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/sendEmail")
    public Map<String,Object> sendUserEmail(HttpServletRequest request,String email) {
        log.debug("-------------------sendUserEmail:发送邮件-------------");
        String bearerToken = request.getHeader("Authorization");
        return userService.sendUserEmail(bearerToken,email);
    }
    @PostMapping("/saveUser")
    public Map<String, Object> saveUser(HttpServletRequest request,
                                        @RequestBody(required = false) UserEntity userEntity,
                                        String code) {
        log.debug("-------------------PostMapping:添加用户-------------");
        String bearerToken = request.getHeader("Authorization");
        return userService.saveUserService(bearerToken,userEntity,code);
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


    @PostMapping("/getUserAll")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页", name = "page", required = true),
            @ApiImplicitParam(value = "一页多少个", name = "pageSize", required = true),
            @ApiImplicitParam(value = "升降序", name = "sort", required = true),
            @ApiImplicitParam(value = "升降序的键", name = "sortKey", required = true),
            @ApiImplicitParam(value = "模糊搜索-user", name = "user", required = false),
            @ApiImplicitParam(value = "模糊搜索-name", name = "name", required = false),
    })
    public Map<String, Object> getUserAll(HttpServletRequest request,
                                          @RequestBody(required = false) Map<String,Object> map) {
        log.debug("-------------------getUserAll:获取全部用户-------------");
        String bearerToken = request.getHeader("Authorization");
        return userService.getUserAllService(bearerToken,map);
    }

    @PostMapping("/deleteUser")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户唯一id", name = "userIds", required = true),
    })
    public Map<String, Object> deleteUser(HttpServletRequest request, @RequestBody(required = false) Map<String, List<Integer>> map) {
        log.debug("-------------------getUserAll:获取全部用户-------------");
        List<Integer> userIds = map.get("userIds");
        String bearerToken = request.getHeader("Authorization");
        return userService.deleteUserService(bearerToken, userIds);
    }
    //重置密码
    @GetMapping("/resetUserPwd")
    public Map<String, Object> resetUserPwd(HttpServletRequest request,int userId) {
        log.debug("-------------------getUserAll:获取全部用户-------------");
        String bearerToken = request.getHeader("Authorization");
        return userService.resetUserPwdService(bearerToken,userId);
    }
}
