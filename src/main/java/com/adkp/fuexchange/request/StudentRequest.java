package com.adkp.fuexchange.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StudentRequest {
    private String studentId;

    private String firstName;

    private String lastName;

    private String identityCard;

    private String address;

    private String phoneNumber;

    private String gender;

    private LocalDate dob;
}
