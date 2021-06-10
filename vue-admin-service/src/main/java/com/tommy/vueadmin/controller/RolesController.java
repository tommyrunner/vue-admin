package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.service.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
