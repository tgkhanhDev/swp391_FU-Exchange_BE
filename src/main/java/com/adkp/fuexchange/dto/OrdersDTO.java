package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDTO {
    int orderId;

    String registeredStudent;

    OrderStatusDTO orderStatus;

    LocalDate createDate;

    LocalDate completeDate;

    String description;

    int paymentId;
}
