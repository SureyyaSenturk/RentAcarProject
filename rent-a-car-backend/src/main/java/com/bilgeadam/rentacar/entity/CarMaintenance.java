package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class CarMaintenance extends BaseEntity {

  @Column private String description;

  @Column private LocalDate returnDate;

  @ManyToOne
  @JoinColumn(name = "car_id")
  private Car car;
}
