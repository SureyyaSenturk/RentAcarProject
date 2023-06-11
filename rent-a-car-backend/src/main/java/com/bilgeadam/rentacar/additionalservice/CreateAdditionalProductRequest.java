package com.bilgeadam.rentacar.additionalservice;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.request.BaseRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalProductRequest extends BaseRequest {

    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,50}", message = BusinessMessages.AdditionalServiceRequestsMessages.ADDITIONAL_PRODUCT_NAME_REGEX_MESSAGE)
    private String name;

    @NotNull
    @Min(0)
    private double dailyPrice;
}
