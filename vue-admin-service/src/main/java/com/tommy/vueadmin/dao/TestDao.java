package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.TestEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestDao extends JpaRepository<TestEntity,Integer> {

    //根据id批量删除
    void deleteTestEntitiesByIdIn(List<Integer> testIds);
    //批量查询+分页+模糊
    List<TestEntity> findAllByValueContainingAndNoteContaining  (String value, String note, Pageable p);
    //根据条件查询总数
    Integer countAllByValueContainingAndNoteContaining  (String value,String note);
}
