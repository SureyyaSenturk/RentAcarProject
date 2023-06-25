package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class Color extends BaseEntity {
    @Column
    private String name;
}
