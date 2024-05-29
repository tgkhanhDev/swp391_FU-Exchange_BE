package com.adkp.fuexchange.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {

    private int idWantUpdate;

    private String password;

    private String confirmPassword;
}
