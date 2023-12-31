package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.entity.Customer;
import javax.persistence.*;
import lombok.*;

@Entity(name = "individualCustomer")
@Getter
@Setter
@Table(name = "individual_customers")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="individual_customer_id",referencedColumnName = "customer_id")
public class IndividualCustomer extends Customer {
  @Column private String firstName;
  @Column private String lastName;
  @Column private String nationalIdentity;
}
