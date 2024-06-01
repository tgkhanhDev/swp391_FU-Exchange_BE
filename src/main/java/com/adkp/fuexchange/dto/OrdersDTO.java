package com.adkp.fuexchange.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersDTO {
    int orderId;
    RegisteredStudentDTO registeredStudentId;
    OrderStatusDTO orderStatusId;
    LocalDateTime createDate;
    LocalDateTime completeDate;
    String description;
}
