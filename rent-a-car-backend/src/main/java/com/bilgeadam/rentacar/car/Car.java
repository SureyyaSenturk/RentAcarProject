package com.bilgeadam.rentacar.car;

import com.bilgeadam.rentacar.brand.Brand;
import com.bilgeadam.rentacar.cardamage.CarDamage;
import com.bilgeadam.rentacar.carmaintenance.CarMaintenance;
import com.bilgeadam.rentacar.color.Color;
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
public class Car extends BaseEntity {

  @Column private double dailyPrice;

  @Column private int modelYear;

  @Column private String description;

  @Column private double kilometerInformation;

  @Column private String imageUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  private Brand brand;

  @ManyToOne(fetch = FetchType.LAZY)
  private Color color;

  @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CarMaintenance> carMaintenances;

  @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<RentalCar> rentalCars;

  @ManyToMany
  @JoinTable(
      name = "cars_car_damages",
      joinColumns = {@JoinColumn(name = "car_id")},
      inverseJoinColumns = {@JoinColumn(name = "car_damage_id")})
  private List<CarDamage> carDamages;
}
