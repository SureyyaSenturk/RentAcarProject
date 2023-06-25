package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.controllers.CreateDelayedPaymentModel;
import com.bilgeadam.rentacar.controllers.CreatePaymentModel;
import com.bilgeadam.rentacar.business.response.PaymentResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface PaymentService {

    DataResult<List<PaymentResponse>> getAll();

    Result paymentForIndividualCustomer(CreatePaymentModel createPaymentModel) ;

    Result paymentForCorporateCustomer(CreatePaymentModel createPaymentModel) ;

    void paymentSuccessorForIndividualCustomer(CreatePaymentModel createPaymentModel) ;

    void paymentSuccessorForCorporateCustomer(CreatePaymentModel createPaymentModel) ;

    void delayedPaymentSuccessor(CreateDelayedPaymentModel createDelayedPaymentModel) ;

    Result delete(String id) ;

    DataResult<PaymentResponse> getById(String id) ;

    Result additionalPaymentForDelaying(CreateDelayedPaymentModel createDelayedPaymentModel) ;
}
