package com.adkp.fuexchange.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRegisteredStudentRequest {

    @Min(value = 1, message = "Vui lòng nhập đầy đủ thông tin!")
    @NotNull(message = "Vui lòng nhập đầy đủ thông tin!")
    private Integer registeredStudentId;

    private Integer isActive;

}
