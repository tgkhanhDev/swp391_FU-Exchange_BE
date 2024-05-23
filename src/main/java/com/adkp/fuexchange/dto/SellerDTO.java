package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.model.RegisteredStudent;
import com.adkp.fuexchange.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SellerDTO {

    private int sellerId;

    @JsonIgnore
    private RegisteredStudent registeredStudentId;

    private Student student;

    private String bankingName;

    private String bankingNumber;
}
