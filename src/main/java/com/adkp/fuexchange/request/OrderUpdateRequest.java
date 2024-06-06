package com.adkp.fuexchange.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int orderId;

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int orderStatusId;

    private LocalDateTime completeDate;

    private String description;
}
