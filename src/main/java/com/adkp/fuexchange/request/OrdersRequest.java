package com.adkp.fuexchange.request;

import jakarta.validation.Valid;
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
public class OrdersRequest {

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int registeredStudentId;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private List<@Valid PostProductRequest> postProductToBuyRequests;

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int paymentMethodId;

    private String description;
}
