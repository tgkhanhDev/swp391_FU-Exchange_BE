package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionsId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "paymentId", referencedColumnName = "paymentId")
    private Payment paymentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "transactionsStatusId", referencedColumnName = "transactionsStatusId")
    private TransactionsStatus transactionsStatusId;

    private double totalPrice;

    private Date createTime;

    private Date completeTime;

    public Transactions(Payment paymentId, TransactionsStatus transactionsStatusId, double totalPrice, Date createTime, Date completeTime) {
        this.paymentId = paymentId;
        this.transactionsStatusId = transactionsStatusId;
        this.totalPrice = totalPrice;
        this.createTime = createTime;
        this.completeTime = completeTime;
    }
}
