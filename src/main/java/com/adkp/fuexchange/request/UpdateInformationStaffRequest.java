package com.adkp.fuexchange.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UpdateInformationStaffRequest {

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    private int staffId;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String firstName;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String lastName;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String gender;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String identityCard;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String address;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private String phoneNumber;

    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    @NotEmpty(message = "Vui lòng nhập đầy đủ thông tin!")
    private LocalDate dob;
}
