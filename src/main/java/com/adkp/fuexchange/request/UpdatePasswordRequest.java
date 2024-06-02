package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {

    private Integer idWantUpdate;

    private String password;

    private String confirmPassword;
}
