package com.bilgeadam.rentacar.brand;

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
public class Brand extends BaseEntity {
  @Column
  private String name;

}
