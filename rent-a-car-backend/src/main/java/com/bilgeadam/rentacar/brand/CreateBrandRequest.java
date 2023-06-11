package com.bilgeadam.rentacar.brand;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.request.BaseRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest extends BaseRequest {

    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,50}", message = BusinessMessages.BrandRequestsMessages.BRAND_NAME_REGEX_MESSAGE)
    private String name;
}

