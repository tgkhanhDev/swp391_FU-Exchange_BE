package com.adkp.fuexchange.dto;

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
@Table(name = "TransactionsStatus")
public class TransactionsStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionsStatusId;

    private String transactionsStatusName;

    @OneToMany(mappedBy = "transactionsId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Transactions> transactionsId;

    public TransactionsStatus(String transactionsStatusName) {
        this.transactionsStatusName = transactionsStatusName;
    }
}
