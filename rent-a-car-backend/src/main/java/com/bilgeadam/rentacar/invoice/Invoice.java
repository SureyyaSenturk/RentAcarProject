package com.bilgeadam.rentacar.invoice;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import com.bilgeadam.rentacar.customer.Customer;
import com.bilgeadam.rentacar.rentalcar.RentalCar;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Invoice extends BaseEntity {

    @Column
    private Integer number;

    @Column
    private LocalDate date;

    @Column
    private Double additionalProductTotalPayment;

    @Column
    private Integer rentDay;

    @Column
    private Double rentPayment;

    @Column
    private Double rentLocationPayment;

    @Column
    private Double totalPayment;

    @OneToOne()
    @JoinColumn(name = "rental_car_id")
    private RentalCar rentalCar;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


}