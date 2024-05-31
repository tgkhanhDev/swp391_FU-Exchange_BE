package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class RegisterToSellerRequest {

    private Integer registeredStudentId;

    private String password;

    private String bankingNumber;

    private String bankingName;

}
