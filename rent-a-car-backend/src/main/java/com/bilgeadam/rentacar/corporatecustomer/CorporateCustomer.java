package com.bilgeadam.rentacar.corporatecustomer;

import com.bilgeadam.rentacar.customer.Customer;
import javax.persistence.*;
import lombok.*;

@Entity(name = "corporateCustomer")
@Getter
@Setter
@Table(name = "corporate_customers")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="corporate_customer_id",referencedColumnName = "customer_id")
public class CorporateCustomer extends Customer {

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "tax_number",length = 10)
	private String taxNumber;

}
