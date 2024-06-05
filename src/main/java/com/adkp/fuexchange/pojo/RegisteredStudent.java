package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@ToString
@AllArgsConstructor
@Builder
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

    private String password;

    private boolean isActive;

    @OneToMany(mappedBy = "registeredStudentId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Orders> orderId;

    @OneToOne(mappedBy = "registeredStudentId", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private Cart cartId;
    public RegisteredStudent(Student studentId, Roles roleId, String password, boolean isActive) {
        this.studentId = studentId;
        this.roleId = roleId;
        this.password = password;
        this.isActive = isActive;
    }
}
