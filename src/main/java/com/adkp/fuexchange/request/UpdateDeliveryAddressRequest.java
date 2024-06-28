package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class UpdateDeliveryAddressRequest {

    private Integer registeredStudentId;

    private String deliveryAddress;
}
