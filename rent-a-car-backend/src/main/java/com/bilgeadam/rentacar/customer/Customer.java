package com.bilgeadam.rentacar.customer;

import com.bilgeadam.rentacar.invoice.Invoice;
import com.bilgeadam.rentacar.payment.Payment;
import com.bilgeadam.rentacar.rentalcar.RentalCar;
import com.bilgeadam.rentacar.user.User;
import com.bilgeadam.rentacar.usercardinformation.UserCardInformation;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity(name = "customer")
@Getter
@Setter
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="customer_id",referencedColumnName = "user_id")
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<RentalCar> rentalCars;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

    @OneToMany(mappedBy = "customer")
    private List<UserCardInformation> userCardInformations;

}
