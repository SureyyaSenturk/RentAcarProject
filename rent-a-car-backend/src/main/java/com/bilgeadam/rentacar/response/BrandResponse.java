package com.bilgeadam.rentacar.response;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse extends BaseResponse {
    private String name;


}