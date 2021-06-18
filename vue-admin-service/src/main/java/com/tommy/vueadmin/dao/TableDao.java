package com.tommy.vueadmin.dao;

import com.tommy.vueadmin.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Tommy
 * 2021/6/9
 */
public interface TableDao extends JpaRepository<TableEntity,Integer> {

}
