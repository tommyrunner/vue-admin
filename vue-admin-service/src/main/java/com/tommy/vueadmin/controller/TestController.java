package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.entity.TestEntity;
import com.tommy.vueadmin.service.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    TestService testService;

    //----
    @PostMapping("/getTestAll")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页", name = "page", required = true),
            @ApiImplicitParam(value = "一页多少个", name = "pageSize", required = true),
            @ApiImplicitParam(value = "升降序", name = "sort", required = true),
            @ApiImplicitParam(value = "升降序的键", name = "sortKey", required = true),
    })
    public Map<String, Object> getTestAll(@RequestBody(required = false) Map<String,Object> map) {
        log.debug("-------------------getUserAll:获取全部Test-------------");
        return testService.getTestAllService(map);
    }
    @PostMapping("/deleteTest")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "唯一id", name = "testIds", required = true),
    })
    public Map<String, Object> deleteTest(@RequestBody(required = false) Map<String, List<Integer>> map) {
        log.debug("-------------------getUserAll:获取全部Test-------------");
        List<Integer> testIds = map.get("testIds");
        return testService.deleteTestService( testIds);
    }
    @PostMapping("/saveTest")
    public Map<String, Object> saveTest(@RequestBody(required = false) TestEntity testEntity) {
        log.debug("-------------------PostMapping:添加Test-------------");
        return testService.saveTestService(testEntity);
    }
}