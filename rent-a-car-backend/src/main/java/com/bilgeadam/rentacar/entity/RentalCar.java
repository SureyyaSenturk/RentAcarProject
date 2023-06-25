package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;

import java.time.LocalDate;
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
public class RentalCar extends BaseEntity {

    @Column
    private LocalDate rentDate;

    @Column
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "rent_city_id")
    private City rentCity;

    @ManyToOne
    @JoinColumn(name = "return_city_id")
    private City returnCity;

    @Column
    private double rentStartKilometer;

    @Column
    private double returnKilometer;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    @ManyToMany
    @JoinTable(name = "rental_cars_additional_services",
            joinColumns = {@JoinColumn(name = "rental_car_id")},
            inverseJoinColumns = {@JoinColumn(name = "additional_service_id")}
    )
    private List<AdditionalProduct> additionalProducts;

}
