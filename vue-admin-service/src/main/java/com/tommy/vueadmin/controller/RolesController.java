package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.service.RolesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/10
 */
@RestController
@RequestMapping("/roles")
@Slf4j
public class RolesController {

    @Autowired
    RolesService rolesService;

    @GetMapping("/getRolesByUserIdAll")
    public Map<String,Object> getRolesByUserIdAll(int userId){
        return  rolesService.getRolesByUserIdAllService(userId);
    }
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户唯一id", name = "userId", required = true),
            @ApiImplicitParam(value = "权限列表Roles", name = "rolesAll", required = true),
    })
    @PostMapping("/saveRolesByUserId")
    public Map<String,Object> saveRolesByUserId(@RequestBody(required = false) Map<String, Object> map){
        return  rolesService.saveRolesByUserService(map);
    }
}
