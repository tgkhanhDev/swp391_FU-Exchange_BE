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
