package com.adkp.fuexchange.request;

import com.adkp.fuexchange.pojo.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariationRequest {


    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private String variationName;

}
