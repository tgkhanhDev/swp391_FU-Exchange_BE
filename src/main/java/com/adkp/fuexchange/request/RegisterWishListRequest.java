package com.adkp.fuexchange.request;

import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterWishListRequest {
    private int postProductId;
    private int registeredStudentId;
    private int quantity;
}
