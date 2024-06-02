package com.adkp.fuexchange.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginResponse {

    private int status;

    private String message;

    private String content;

    private InforLoginResponse data;
}
