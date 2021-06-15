package com.tommy.vueadmin.service;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.code.kaptcha.Producer;
import com.tommy.vueadmin.aop.ApiUserAuth;
import com.tommy.vueadmin.dao.UserDao;
import com.tommy.vueadmin.dao.RolesDao;
import com.tommy.vueadmin.dao.UserPwdDao;
import com.tommy.vueadmin.dao.UserRolesDao;
import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.TokenEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.entity.UserPwdEntity;
import com.tommy.vueadmin.utils.DataBase;
import com.tommy.vueadmin.utils.MyUtils;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import com.tommy.vueadmin.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author Tommy
 * 2021/6/4
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    UserDao userDao;//用户dao
    @Autowired
    RolesDao rolesDao;
    @Autowired
    UserRolesDao userRolesDao;//用户和权限关系表
    @Autowired
    RedisTemplate redisTemplate;     //redis k-v操作字符串
    @Autowired
    private Producer producer;  //生成图片验证码
    @Autowired
    private UserPwdDao userPwdDao;//密码记录
    @Autowired
    private JavaMailSender javaMailSender;  //发送邮箱

    //    验证码
    public BufferedImage loginCodeService(HttpServletResponse response, String uuid) {
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
        redisTemplate.opsForValue().set(uuid, count, 1, TimeUnit.MINUTES);
        return image;
    }


    //登录
    public Map<String, Object> loginUserService(Map<String, Object> map) {
        //校验
        if (map == null || map.get("user") == null || map.get("pwd") == null || map.get("code") == null || map.get("uuid") == null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "缺少参数", null);
        }
        String user = (String) map.get("user");
        String pwd = (String) map.get("pwd");
        String code = (String) map.get("code");
        String uuid = (String) map.get("uuid");
        Object o = redisTemplate.opsForValue().get(uuid);
        if (o == null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "验证码错误,请刷新验证码!", null);
        }
        int uuidValue = (Integer) o;
        if (uuidValue != Integer.parseInt(code)) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "验证答案错误!", null);

        }
        if ("".equals(user) || "".equals(pwd) || "".equals(code) || "".equals(uuid)) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "参数不能为空", null);
        }
        //获取uuid验证验证码
        if (uuid.equals("")) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_PARA, "缺少设备!", null);
        }
        //从redis获取验证码答案

        UserEntity byUser = userDao.findByUser(user);
        //验证用户是否存在
        if (byUser == null) {
            //用户存在
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户不存在", null);
        }
        //验证密码是否正确
        if (!DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(byUser.getPwd())) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "密码不正确!", null);
        }
        //去掉不需要显示内容
        byUser.setPwd("*********");
        //获取用户权限
        List<RolesEntity> rolesUsers = rolesDao.findRolesByUserId(byUser.getId());
        //生成token
        String token = TokenUtil.sign(byUser, rolesUsers);
        //cun
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setTime(new Date().getTime());
        tokenEntity.setToken(token);
        redisTemplate.opsForValue().set(byUser.getUser(), JSONObject.toJSONString(tokenEntity));
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "登录成功!", token);
    }

    //通过token获取用户详情
    public Map<String, Object> getUserInfoService(String token) {
        DecodedJWT jwt = TokenUtil.verify(token);
        if (jwt == null) {
            //自动过期时间-token错误/或者过期
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_TOKEN, "身份错误或失效", null);
        } else {
            //封装map
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userInfo", jwt.getClaim("userInfo").asString());
            userInfo.put("roles", jwt.getClaim("roles").asString());
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", userInfo);
        }
    }

    //    退出登录
    public Map<String, Object> loginOutService(String token) {
        DecodedJWT jwt = TokenUtil.verify(token);
        if (jwt == null) {
            //自动过期时间-token错误/或者过期
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR_TOKEN, "身份错误或失效", null);
        } else {
            //获取转对象
            UserEntity userEntity = JSONObject.parseObject(jwt.getClaim("userInfo").asString(), UserEntity.class);
            //删除token
            redisTemplate.delete(userEntity.getUser());
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!", userEntity.getUser());
        }
    }

    //获取所有用户
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> getUserAllService(String token, Map<String,Object> map) {
        //获取相应参数
        Integer page = (Integer) map.getOrDefault("page",1);
        Integer pageSize = (Integer) map.getOrDefault("pageSize",10);
        String sort = (String) map.getOrDefault("sort","DESC");
        String sortKey = (String) map.getOrDefault("sortKey","id");
        String user = (String) map.getOrDefault("user","");
        String name = (String) map.getOrDefault("name","");
        Sort sortObj = Sort.by(MyUtils.getSort(sort), sortKey);//根据名字和年龄倒叙
        Pageable pageable = PageRequest.of(page-1, pageSize,sortObj);
        List<UserEntity> containingUserEntity =
                userDao.findAllByUserContainingAndNameContainingAndIsDel(user,name,pageable,false);
        //过滤
        for(UserEntity item : containingUserEntity){
            item.setPwd("***");
        }
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("size",userDao.userCount());
        returnMap.put("list",containingUserEntity);

        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", returnMap);
    }

    //添加修改-用户
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> saveUserService(String token, UserEntity userEntity, String mapCode) {
        //校验
        UserEntity byUser = userDao.findByUser(userEntity.getUser());
        if (userEntity.getUser().equals(DataBase.ADMIN_USERNAME) || userEntity.getId() == 1) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "不能操作超级用户!", null);
        }
        //判断是否添加还是修改
        boolean isAdd = userEntity.getId() == 0;
        //如果是添加
        if (isAdd) {
            //判断是否已经存在
            if (byUser != null) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, byUser.isDel() ? "用户已删除!" : "用户已存在!", null);
            }
            //判断验证码是否正确
            Object emailCode = redisTemplate.opsForValue().get("tommy-email-" + userEntity.getUser());
            //获取输入的验证码
            if ("".equals(emailCode) || mapCode == null || "".equals(mapCode) || !mapCode.equals(emailCode)) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "验证码错误,请重新获取!", null);
            }
            //此时验证码正确
            //密码加密
            userEntity.setPwd(DigestUtils.md5DigestAsHex(userEntity.getPwd().getBytes()));
        } else {
            //判断是否已经存在
            if (byUser == null) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR,  "用户不存在!", null);
            }
            //如果是修改-不能修改密码,不能修改状态,账号
            userEntity.setDel(byUser.isDel());
            userEntity.setUser(byUser.getUser());
            userEntity.setPwd(byUser.getPwd());
        }
        UserEntity save = userDao.save(userEntity);
        //如果是添加,就记录密码
        if(isAdd){
            //将密码保存至记录表中
            UserPwdEntity userPwdEntity = new UserPwdEntity();
            userPwdEntity.setPwd(save.getPwd());
            userPwdEntity.setUserId(save.getId());
            userPwdEntity.setCreateTime(String.valueOf(new Date().getTime()));
            userPwdDao.save(userPwdEntity);
        }

        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, isAdd ? "添加成功!" : "修改成功!", save.getUser());
    }
    //重置密码
    //根据用户邮件生成验证码,并保存redis
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> resetUserPwdService(String token, int userId) {
        UserEntity userEntity = userDao.findById(userId).get();
        if(userEntity.getUser() == null){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户不存在!", null);
        }
        UserPwdEntity byUserId = userPwdDao.findByUserId(userId);
        if(byUserId==null){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户密码异常,不能重置!", null);
        }
        //重置密码
        userEntity.setPwd(byUserId.getPwd());
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!",userEntity.getUser());
    }

    //根据用户邮件生成验证码,并保存redis
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    public Map<String, Object> sendUserEmail(String token, String email) {
        //校验
        Matcher emailMatcher = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$").matcher(email);
        if (!emailMatcher.matches()) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "错误的邮箱", null);
        }
        UserEntity byUser = userDao.findByUser(email);
        if (byUser != null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户已存在!", null);
        }
        Object o = redisTemplate.opsForValue().get("tommy-email-" + email);
        if (o != null) {
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "不能重复获取验证码!", null);
        }
        //邮箱接口实例
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //发送邮箱号
        simpleMailMessage.setFrom("1223758238@qq.com");
        //接收邮箱号
        simpleMailMessage.setTo(email);
        //主题（标题)
        simpleMailMessage.setSubject("验证码");
        //生成四位数验证码
        String code = String.valueOf(new Random().nextInt(8888)+1000);
        //主体内容
        simpleMailMessage.setText(String.format("[%s]尊敬的用户，本次验证码:%s，有效期60秒。", email, code));
        //发送
        javaMailSender.send(simpleMailMessage);
        //保存验证码
        //保存 redis key 自己设置
        redisTemplate.opsForValue().set("tommy-email-" + email, code, 1, TimeUnit.MINUTES);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "发送成功,有效期60秒!", null);
    }

    //批量删除用户
    @ApiUserAuth(msg = "没有权限哦!", auth = DataBase.ADMIN_USERNAME)
    @Transactional
    public Map<String, Object> deleteUserService(String token, List<Integer> userIds) {
        List<UserEntity> userEntitiesByIdIn = userDao.findUserEntitiesByIdIn(userIds);
        for (UserEntity userEntity : userEntitiesByIdIn) {
            if (userEntity == null) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户不存在!", null);
            }
            if (userEntity.getUser().equals(DataBase.ADMIN_USERNAME)) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "不能操作超级用户!", null);
            }
            if (userEntity.isDel()) {
                return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "用户已被删除!", null);
            }
            //修改删除用户
            userEntity.setDel(true);
        }
//      批量保存
        userDao.saveAll(userEntitiesByIdIn);
//        //删除关于用户的权限
        userRolesDao.deleteUserRolesEntitiesByUserIdIn(userIds);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "删除成功!", userIds);
    }
}
