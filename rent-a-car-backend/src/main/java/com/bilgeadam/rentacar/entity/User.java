package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity(name = "user")
@Getter
@Setter
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="user_id",referencedColumnName = "id")
public class User extends BaseEntity {

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String authority;
}
