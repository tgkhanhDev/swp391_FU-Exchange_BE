package com.adkp.fuexchange.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class InforLoginResponse {
    private String username;

    private String role;

    private String accessToken;
}
