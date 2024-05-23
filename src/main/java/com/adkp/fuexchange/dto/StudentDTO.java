package com.adkp.fuexchange.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class StudentDTO {

    private String studentId;

    private String firstName;

    private String lastName;

    private String identityCard;

    private String address;

    private String phoneNumber;

    private String gender;

    private Date dob;
}
