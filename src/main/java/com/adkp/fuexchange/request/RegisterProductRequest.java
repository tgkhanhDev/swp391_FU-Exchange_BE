package com.adkp.fuexchange.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RegisterProductRequest {

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String productName;

    private String productDescription;

    private String studentId;

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int categoryId;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private double price;

    private boolean productStatus;

    List<@Valid VariationRequest> variationList;

    List<@Valid ProductImageRequest> productImageRequestsList;

}
