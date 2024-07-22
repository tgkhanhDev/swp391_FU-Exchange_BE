package com.adkp.fuexchange.request;

import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.pojo.PostProduct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterReviewRequest {

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int postProductId;

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int orderId;

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int ratingNumber;

    private String description;
}
