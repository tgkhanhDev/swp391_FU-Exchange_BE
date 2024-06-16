package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sellerId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    private String bankingNumber;

    private String bankingName;

    private boolean isActive;

    public Seller(RegisteredStudent registeredStudentId, String bankingNumber, String bankingName, boolean isActive) {
        this.registeredStudentId = registeredStudentId;
        this.bankingNumber = bankingNumber;
        this.bankingName = bankingName;
        this.isActive = isActive;
    }
}
