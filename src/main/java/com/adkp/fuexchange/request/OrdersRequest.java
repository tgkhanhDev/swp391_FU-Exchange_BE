package com.adkp.fuexchange.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrdersRequest {

    private int registeredStudentId;

    private List<PostProductRequest> postProductToBuyRequests;

    private int paymentMethodId;

    private String description;
}
