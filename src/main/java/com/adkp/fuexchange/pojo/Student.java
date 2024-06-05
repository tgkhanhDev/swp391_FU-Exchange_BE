package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Builder
@AllArgsConstructor
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

    private LocalDate dob;

}
