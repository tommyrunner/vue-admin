package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.entity.RolesEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.service.RolesService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    //----
    @PostMapping("/getRolesAll")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页", name = "page", required = true),
            @ApiImplicitParam(value = "一页多少个", name = "pageSize", required = true),
            @ApiImplicitParam(value = "升降序", name = "sort", required = true),
            @ApiImplicitParam(value = "升降序的键", name = "sortKey", required = true),
    })
    public Map<String, Object> getRolesAll(HttpServletRequest request,
                                          @RequestBody(required = false) Map<String,Object> map) {
        log.debug("-------------------getUserAll:获取全部菜单-------------");
        String bearerToken = request.getHeader("Authorization");
        return rolesService.getRolesAllService(bearerToken,map);
    }
    @PostMapping("/deleteRoles")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "菜单唯一id", name = "rolesIds", required = true),
    })
    public Map<String, Object> deleteRoles(HttpServletRequest request, @RequestBody(required = false) Map<String, List<Integer>> map) {
        log.debug("-------------------getUserAll:获取全部菜单-------------");
        List<Integer> rolesIds = map.get("rolesIds");
        String bearerToken = request.getHeader("Authorization");
        return rolesService.deleteRolesService(bearerToken, rolesIds);
    }
    @PostMapping("/saveRoles")
    public Map<String, Object> saveRoles(HttpServletRequest request,
                                        @RequestBody(required = false) RolesEntity rolesEntity) {
        log.debug("-------------------PostMapping:添加菜单-------------");
        String bearerToken = request.getHeader("Authorization");
        return rolesService.saveRolesService(bearerToken,rolesEntity);
    }

    @PostMapping("/syncRoles")
    public Map<String, Object> syncRoles(HttpServletRequest request,
                                         @RequestBody(required = false) List<RolesEntity> rolesEntities) {
        log.debug("-------------------PostMapping:添加菜单-------------");
        String bearerToken = request.getHeader("Authorization");
        return rolesService.syncRolesService(bearerToken,rolesEntities);
    }
}
