package com.tommy.vueadmin.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Tommy
 * 2021/6/15
 */
@Entity
@Table(name = "tb_user_pwd", uniqueConstraints=@UniqueConstraint(columnNames={"id", "userId"}))
@Data
//用户
public class UserPwdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;
    @Column(nullable = false)
    int userId;
    @Column(nullable = false)
    String pwd;
    @Column(nullable = false)
    String createTime;
    @Column
    String note;
}
