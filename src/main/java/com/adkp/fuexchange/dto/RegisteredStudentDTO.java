package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class RegisteredStudentDTO {

    private int registeredStudentId;

    private StudentDTO student;

    private RoleDTO role;

    private String password;

    private boolean active;

    private List<Orders> order;
}
