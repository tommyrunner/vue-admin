package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Tommy
 * 2021/6/6
 */
@Entity
@Table(name = "tb_roles",uniqueConstraints=@UniqueConstraint(columnNames={"id", "roles"}))
@Data
public class RolesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = 0;
    @Column(nullable = false)
    String roles;
    @Column(nullable = false)
    String path;
    @Column(nullable = false)
    String icon;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String note;
}
