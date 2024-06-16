package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SellerDTO {

    private int sellerId;

    private StudentDTO student;

    private String bankingName;

    private String bankingNumber;

    private boolean active;

}
