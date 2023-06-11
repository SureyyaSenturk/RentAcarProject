package com.bilgeadam.rentacar.pos;

import com.bilgeadam.rentacar.payment.CreatePaymentRequest;
import com.bilgeadam.rentacar.util.results.Result;

public interface PosService {

    Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest);
}
