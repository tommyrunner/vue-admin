package com.tommy.vueadmin.service;

import com.alibaba.fastjson.JSONObject;
import com.tommy.vueadmin.dao.TableDao;
import com.tommy.vueadmin.entity.*;
import com.tommy.vueadmin.utils.MyUtils;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.HandshakeResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    @Autowired
    TableDao tableDao;

    //查询+模糊
    public Map<String, Object> getTableAllService( SearchEntity<TableEntity> searchEntity) {
        //获取相应参数
        Sort sortObj = Sort.by(MyUtils.getSort(searchEntity.getSort()), searchEntity.getSortKey());//获取检索key
        Pageable pageable = PageRequest.of(searchEntity.getPage() - 1, searchEntity.getPageSize(), sortObj); //获取分页
        TableEntity tableEntity = searchEntity.getContaining()==null?new TableEntity():searchEntity.getContaining();//获取模糊对象
        //查询
        List<TableEntity> allContains = tableDao.findAllByValueContainingAndUserContainingAndNoteContaining
                (  tableEntity.getValue(), tableEntity.getUser(), tableEntity.getNote(),  pageable);
        //封装
        Map<String, Object> returnMap = new HashMap<>();
        //查询全部
        returnMap.put("size", tableDao.countAllByValueContainingAndUserContainingAndNoteContaining
                (  tableEntity.getValue(), tableEntity.getUser(), tableEntity.getNote()));
        returnMap.put("list", allContains);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", returnMap);
    }

    //批量删除
    @Transactional
    public Map<String, Object> deleteTableService(List<Integer> tableIds) {
        tableDao.deleteTableEntitiesByIdIn(tableIds);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "删除成功!", tableIds.size());
    }

    //保存-修改
    public Map<String, Object> saveTableService(TableEntity tableEntity) {
        //判断是否添加还是修改
        boolean isAdd = tableEntity.getId() == 0;
        //如果是添加
        TableEntity save = tableDao.save(tableEntity);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, isAdd ? "添加成功!" : "修改成功!", save);
    }

    //导出
    public void exportDataService(HttpServletResponse response, SearchEntity<TableEntity> searchEntity) {
        Sort sortObj = Sort.by(MyUtils.getSort(searchEntity.getSort()), searchEntity.getSortKey());
        TableEntity tableEntity = searchEntity.getContaining()==null?new TableEntity():searchEntity.getContaining();
        Pageable pageable = PageRequest.of(searchEntity.getPage() - 1, searchEntity.getPageSize(), sortObj);
        List<TableEntity> allContains = tableDao.findAllByValueContainingAndUserContainingAndNoteContaining
                (tableEntity.getValue(), tableEntity.getUser(), tableEntity.getNote(), pageable);
        //导出操作
        PoiExcelUtil.exportExcel(allContains, "vue-admin 导出数据", "sheet1", TableEntity.class, "file.xlsx", response);
    }

    //导入
    @Transactional
    public Map<String, Object> importDataService(MultipartFile file) {
        //导出操作
        List<TableEntity> testEntities = PoiExcelUtil.importExcel(file, 1, 1, TableEntity.class);
        //添加全部
        List<TableEntity> tableEntities = tableDao.saveAll(testEntities);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "操作成功!", tableEntities.size());
    }
}