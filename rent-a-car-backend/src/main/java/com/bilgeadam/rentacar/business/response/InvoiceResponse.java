package com.bilgeadam.rentacar.business.response;

import com.bilgeadam.rentacar.common.response.BaseResponse;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse extends BaseResponse {

    private Integer number;

    private LocalDate date;

    private Double additionalProductTotalPayment;

    private Integer rentDay;

    private Double rentPayment;

    private Double rentLocationPayment;

    private Double totalPayment;

    private RentalCarResponse rentalCarResponse;

}
