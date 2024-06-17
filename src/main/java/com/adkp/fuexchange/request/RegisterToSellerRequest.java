package com.adkp.fuexchange.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterToSellerRequest {

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int registeredStudentId;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private String password;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private String bankingNumber;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private String bankingName;

}
