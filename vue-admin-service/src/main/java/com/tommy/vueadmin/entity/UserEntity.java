package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_user", uniqueConstraints=@UniqueConstraint(columnNames={"id", "user"}))
@Data
//用户
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = 0;
    @Column(nullable = false)
    String user = "";
    @Column(nullable = false)
    String name = "";
    @Column(nullable = false)
    String pwd;
    @Column(nullable = true)
    String system_theme;
    @Column(nullable = true)
    String system_lang;
    @Column(nullable = false)
    boolean isDel;
    @Column(nullable = false)
    String note;
    //覆盖-返回的时候有id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
}
