package com.bilgeadam.rentacar.city;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import com.bilgeadam.rentacar.rentalcar.RentalCar;
import java.util.List;
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
public class City extends BaseEntity {

  @Column private Integer code;
  @Column private String name;

  @OneToMany(mappedBy = "rentCity")
  private List<RentalCar> rentalCarRentCity;

  @OneToMany(mappedBy = "returnCity")
  private List<RentalCar> rentalCarReturnCity;
}
