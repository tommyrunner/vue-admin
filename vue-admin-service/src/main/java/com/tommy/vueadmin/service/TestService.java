package com.tommy.vueadmin.service;

import com.tommy.vueadmin.dao.TestDao;
import com.tommy.vueadmin.entity.SearchEntity;
import com.tommy.vueadmin.entity.TestEntity;
import com.tommy.vueadmin.utils.MyUtils;
import com.tommy.vueadmin.utils.ReturnDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    TestDao testDao;

    //查询+模糊
    public Map<String, Object> getTestAllService( SearchEntity<TestEntity> searchEntity) {
        //获取相应参数
        Sort sortObj = Sort.by(MyUtils.getSort(searchEntity.getSort()), searchEntity.getSortKey());//获取检索key
        Pageable pageable = PageRequest.of(searchEntity.getPage() - 1, searchEntity.getPageSize(), sortObj); //获取分页
        TestEntity testEntity = searchEntity.getContaining()==null?new TestEntity():searchEntity.getContaining();//获取模糊对象
        //查询
        List<TestEntity> allContains = testDao.findAllByValueContainingAndNoteContaining
                (  testEntity.getValue(), testEntity.getNote(),  pageable);
        //封装
        Map<String, Object> returnMap = new HashMap<>();
        //查询全部
        returnMap.put("size", testDao.countAllByValueContainingAndNoteContaining
                (  testEntity.getValue(), testEntity.getNote() ));
        returnMap.put("list", allContains);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "获取成功!", returnMap);
    }
    //批量删除
    @Transactional
    public Map<String, Object> deleteTestService( List<Integer> testIds) {
         testDao.deleteTestEntitiesByIdIn(testIds);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, "删除成功!", testIds.size());
    }
   //保存-修改
    public Map<String, Object> saveTestService( TestEntity testEntity) {
        //判断是否添加还是修改
        boolean isAdd = testEntity.getId() == 0;
        //如果是添加
        TestEntity save = testDao.save(testEntity);
        return ReturnDateUtil.returnData(ReturnDateUtil.CODE_OK, isAdd ? "添加成功!" : "修改成功!", save);
    }
}