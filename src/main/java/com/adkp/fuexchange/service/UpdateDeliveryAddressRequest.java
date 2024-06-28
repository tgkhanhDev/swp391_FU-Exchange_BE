package com.adkp.fuexchange.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateDeliveryAddressRequest {
    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private Integer registeredStudentId;

    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String deliveryAddress;
}
