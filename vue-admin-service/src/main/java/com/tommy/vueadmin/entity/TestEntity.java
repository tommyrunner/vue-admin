package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_test")
@Data
//数据
public class TestEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = 0;
    @Column(nullable = false)
    String value  = "";
    @Column (nullable = false) 
    String note  = "";
}