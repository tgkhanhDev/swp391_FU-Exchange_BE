package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@ToString
@NoArgsConstructor(force = true)
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

    @Column(nullable = false)
    private String password;

    @Column(name = "isActive")
    private boolean isActive;

    public RegisteredStudent(Student studentId, Roles roleId, String password, boolean isActive) {
        this.studentId = studentId;
        this.roleId = roleId;
        this.password = password;
        this.isActive = isActive;
    }
}
