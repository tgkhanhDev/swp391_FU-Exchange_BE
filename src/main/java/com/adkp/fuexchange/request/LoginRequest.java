package com.adkp.fuexchange.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String username;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String password;
}
