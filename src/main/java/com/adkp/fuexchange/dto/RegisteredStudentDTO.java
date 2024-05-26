package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class RegisteredStudentDTO {

    private int registeredStudentId;

    private StudentDTO student;

    private RoleDTO role;

    private boolean active;

    @JsonIgnore
    private String password;

}
