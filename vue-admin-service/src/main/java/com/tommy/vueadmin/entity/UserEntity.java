package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_user")
@Data
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String user;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String pwd;
    @Column(nullable = false)
    String system_theme;
    @Column(nullable = false)
    String system_lang;
    @Column(nullable = false)
    String note;
}