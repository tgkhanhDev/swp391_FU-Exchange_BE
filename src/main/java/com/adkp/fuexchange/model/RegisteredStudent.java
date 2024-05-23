package com.adkp.fuexchange.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "RegisteredStudent")
public class RegisteredStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registeredStudentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    private Student studentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    private Roles roleId;

    @Column(nullable = false)
    private String password;

    public RegisteredStudent(Student studentId, Roles roleId, String password) {
        this.studentId = studentId;
        this.roleId = roleId;
        this.password = password;
    }
}
