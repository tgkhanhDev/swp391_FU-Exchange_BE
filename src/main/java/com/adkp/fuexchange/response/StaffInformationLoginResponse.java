package com.adkp.fuexchange.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffInformationLoginResponse {
    private String username;

    private String staffId;

    private String gender;

    private String identityCard;

    private String phoneNumber;

    private String role;

    private LocalDate dob;

    private String accessToken;
}
