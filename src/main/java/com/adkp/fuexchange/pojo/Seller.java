package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Seller")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String sellerId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    private String bankingNumber;

    private String bankingName;

    @Column(name = "isActive")
    private boolean active;

    public Seller(RegisteredStudent registeredStudentId, String bankingNumber, String bankingName, boolean active) {
        this.registeredStudentId = registeredStudentId;
        this.bankingNumber = bankingNumber;
        this.bankingName = bankingName;
        this.active = active;
    }
}
