package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.TableEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableDao extends JpaRepository<TableEntity,Integer> {

    //根据id批量删除
    void deleteTableEntitiesByIdIn(List<Integer> tableIds);
    //批量查询+分页+模糊
    List<TableEntity> findAllByValueContainingAndUserContainingAndNoteContaining  (String value, String user, String note, Pageable p);
    //根据条件查询总数
    Integer countAllByValueContainingAndUserContainingAndNoteContaining  (String value,String user,String note);
}
