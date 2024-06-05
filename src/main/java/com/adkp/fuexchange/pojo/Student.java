package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
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
