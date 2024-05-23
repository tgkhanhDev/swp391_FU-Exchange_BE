package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.model.Roles;
import com.adkp.fuexchange.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class RegisteredStudentDTO {

    private int registeredStudentId;

    private StudentDTO student;

    private RoleDTO role;

    @JsonIgnore
    private String password;
}
