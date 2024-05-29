package com.adkp.fuexchange.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RegisterRequest {

    private String studentId;
    private String identifyNumber;
    private String password;
    private String confirmPassword;
}
