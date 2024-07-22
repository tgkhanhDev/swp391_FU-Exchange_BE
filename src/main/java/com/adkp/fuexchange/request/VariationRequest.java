package com.adkp.fuexchange.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariationRequest {

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String variationName;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private List<@Valid VariationDetailRequest> variationDetailRequestList;
}
