package com.adkp.fuexchange.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class LoginRequest {

    private String username;

    private String password;
}
