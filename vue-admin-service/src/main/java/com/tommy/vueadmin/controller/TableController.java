package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.entity.SearchEntity;
import com.tommy.vueadmin.entity.TableEntity;
import com.tommy.vueadmin.service.TableService;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/table")
@Slf4j
public class TableController {

    @Autowired
    TableService tableService;


    //----
    @PostMapping("/getTableAll")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页", name = "page", required = true),
            @ApiImplicitParam(value = "一页多少个", name = "pageSize", required = true),
            @ApiImplicitParam(value = "升降序", name = "sort", required = true),
            @ApiImplicitParam(value = "升降序的键", name = "sortKey", required = true),
            @ApiImplicitParam(value = "模糊搜索Obj", name = "containing", required = false),
    })
    public Map<String, Object> getTableAll(@RequestBody(required = false) SearchEntity<TableEntity> searchEntity) {
        log.debug("-------------------getUserAll:获取全部Table-------------");
        return tableService.getTableAllService(searchEntity);
    }
    @PostMapping("/deleteTable")
    //必传参数,提示客户端
    @ApiImplicitParams({
            @ApiImplicitParam(value = "唯一id", name = "tableIds", required = true),
    })
    public Map<String, Object> deleteTable(@RequestBody(required = false) Map<String, List<Integer>> map) {
        log.debug("-------------------deleteTable:获取全部Table-------------");
        List<Integer> tableIds = map.get("tableIds");
        return tableService.deleteTableService( tableIds);
    }
    @PostMapping("/saveTable")
    public Map<String, Object> saveTable(@RequestBody(required = false) TableEntity tableEntity) {
        log.debug("-------------------saveTable:添加Table-------------");
        return tableService.saveTableService(tableEntity);
    }
    @PostMapping("/exportData")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前页", name = "page", required = true),
            @ApiImplicitParam(value = "一页多少个", name = "pageSize", required = true),
            @ApiImplicitParam(value = "升降序", name = "sort", required = true),
            @ApiImplicitParam(value = "升降序的键", name = "sortKey", required = true),
            @ApiImplicitParam(value = "模糊搜索Obj", name = "containing", required = false),
    })
    public void exportData(HttpServletResponse response,@RequestBody(required = false) SearchEntity<TableEntity> searchEntity) {
        log.debug("-------------------exportData:导出-------------");
        tableService.exportDataService(response,searchEntity);
    }
    @PostMapping("/importData")
    public Map<String,Object> importExcel( MultipartFile file) {
        try {
            log.debug("-------------------importData:导入-------------");
            return tableService.importDataService(file);
        }catch (Exception e){
            return ReturnDateUtil.returnData(ReturnDateUtil.CODE_ERROR, "操作失败-数据错误!", null);
        }

    }
}