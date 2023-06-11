package com.bilgeadam.rentacar.additionalservice;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class AdditionalProduct extends BaseEntity {

  @Column private String name;

  @Column private double dailyPrice;
}
