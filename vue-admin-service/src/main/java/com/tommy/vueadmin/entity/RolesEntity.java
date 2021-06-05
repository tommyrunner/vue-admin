package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_roles")
@Data
public class RolesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
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