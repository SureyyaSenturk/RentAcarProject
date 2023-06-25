package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;

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
@Table(name = "payments")
public class Payment extends BaseEntity {

  @Column(name = "card_no", length = 16)
  private String cardNo;

  @Column(name = "card_holder")
  private String cardHolder;

  @Column(name = "expiration_month")
  private int expirationMonth;

  @Column(name = "expiration_year")
  private int expirationYear;

  @Column(name = "cvv")
  private int cvv;

  @Column(name = "payment_amount")
  private Double paymentAmount;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "rental_car_id")
  private RentalCar rentalCar;
}
