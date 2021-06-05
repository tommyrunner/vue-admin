package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_user_roles")
@Data
public class UserRolesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    int userId;
    @Column(nullable = false)
    int rolesId;
    @Column(nullable = false)
    String note;
}
