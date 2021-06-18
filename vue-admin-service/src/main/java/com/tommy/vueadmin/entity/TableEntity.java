package com.tommy.vueadmin.entity;

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
    @Column(nullable = false)
    Integer userId;
    @Column(nullable = false)
    String value;
    @Column(nullable = true)
    String note;
}