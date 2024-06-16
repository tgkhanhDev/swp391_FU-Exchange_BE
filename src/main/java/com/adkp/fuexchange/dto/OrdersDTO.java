package com.adkp.fuexchange.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDTO {
    int orderId;

    String registeredStudent;

    OrderStatusDTO orderStatus;

    LocalDateTime createDate;

    LocalDateTime completeDate;

    String description;

    int paymentId;
}
