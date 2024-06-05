package com.adkp.fuexchange.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequest {
    private int orderId;
    private int orderStatusId;

    private LocalDateTime completeDate;

    private String description;
}
