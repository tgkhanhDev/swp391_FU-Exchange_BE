package com.adkp.fuexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    public TransactionsStatus(String transactionsStatusName) {
        this.transactionsStatusName = transactionsStatusName;
    }
}
