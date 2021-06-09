package com.tommy.vueadmin.controller;

import com.tommy.vueadmin.entity.TableEntity;
import com.tommy.vueadmin.entity.TokenEntity;
import com.tommy.vueadmin.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Table;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/9
 */

@RestController
@RequestMapping("/table")
@Slf4j
public class TableController {

    @Autowired
    TableService tableService;

    @GetMapping("/getTableAll")
    public Map<String,Object> getTableAll(){
        return tableService.getTableAllService();
    }
    @PostMapping("/saveTable")
    public Map<String,Object> saveTable(@RequestBody(required = false) TableEntity tableEntity){
        return tableService.saveTableService(tableEntity);
    }
    @PostMapping("/deleteTable")
    public Map<String,Object> deleteTable(@RequestBody(required = false) TableEntity tableEntity){
        return tableService.deleteTableService(tableEntity);
    }
}
