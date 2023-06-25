package com.bilgeadam.rentacar.request;

import com.bilgeadam.rentacar.common.request.BaseRequest;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest extends BaseRequest {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate rentDate;

    @Nullable
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate returnDate;

    @NotNull
    @Min(1)
    private String rentCityId;

    @NotNull
    @Min(1)
    private String returnCityId;

    @Min(0)
    private double returnKilometer;

    @NotNull
    @Min(1)
    private String carId;

    @NotNull
    @Min(1)
    private String customerId;

    @Nullable
    private List<String> AdditionalServiceIds;


}
