package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String studentId;

    private String identifyNumber;

    private String password;

    private String confirmPassword;
}
