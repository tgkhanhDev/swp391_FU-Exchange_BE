package com.adkp.fuexchange.request;

import com.adkp.fuexchange.pojo.ProductDetail;
import com.adkp.fuexchange.pojo.Variation;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateInformationProductRequest {
    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private Integer productID;

    private String productDetailIdProductName;

    private String productDetailIdDescription;

    private Integer categoryId;

    private double price;


    private Integer variationId;

    private String variationName;

    private Integer variationDetailId;

    private String variationDescription;


}
