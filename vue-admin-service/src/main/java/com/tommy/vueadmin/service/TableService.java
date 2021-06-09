package com.tommy.vueadmin.service;

import com.tommy.vueadmin.aop.ApiUserAuth;
import com.tommy.vueadmin.dao.TableDao;
import com.tommy.vueadmin.entity.TableEntity;
import com.tommy.vueadmin.entity.UserEntity;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Tommy
 * 2021/6/9
 */
@Service
public class TableService {

    //表格dao
    @Autowired
    TableDao tableDao;

    //获取所有用户
    public Map<String, Object> getTableAllService() {
        List<TableEntity> all = tableDao.findAll();
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", all);
    }
    //添加/修改
    public Map<String, Object> saveTableService(TableEntity tableEntity) {
        TableEntity save = tableDao.save(tableEntity);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!", save);
    }
    //删除
    public Map<String, Object> deleteTableService(TableEntity tableEntity) {
        tableDao.deleteById(tableEntity.getId());
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!", tableEntity.getId());
    }
}
