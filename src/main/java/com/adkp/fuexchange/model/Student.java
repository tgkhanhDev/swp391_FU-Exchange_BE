package com.adkp.fuexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Student")
public class Student {
    @Id
    private String studentId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String identityCard;

    private String address;

    private String phoneNumber;

    private String gender;

    private Date dob;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    public Student(String firstName, String lastName, String identityCard, String address, String phoneNumber, String gender, Date dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dob = dob;
    }
}
