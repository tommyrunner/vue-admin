package com.tommy.vueadmin.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_table")
@Data
//数据
public class TableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = 0;
    @Excel(name = "值", orderNum = "0")
    @Column(nullable = false)
    String value  = "";
    @Excel(name = "用户", orderNum = "0")
    @Column (nullable = false) 
    String user  = "";
    @Excel(name = "备注", orderNum = "0")
    @Column (nullable = false) 
    String note  = "";
}