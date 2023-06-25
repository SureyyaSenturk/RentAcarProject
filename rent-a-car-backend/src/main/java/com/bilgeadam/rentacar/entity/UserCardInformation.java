package com.bilgeadam.rentacar.entity;

import com.bilgeadam.rentacar.common.entity.BaseEntity;
import com.bilgeadam.rentacar.entity.Customer;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class UserCardInformation extends BaseEntity {
  @Column private String cardNo;

  @Column private String cardHolder;

  @Column private Integer expirationMonth;

  @Column private Integer expirationYear;

  @Column private Integer cvv;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
