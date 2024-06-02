package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "paymentMethodId", referencedColumnName = "paymentMethodId")
    private PaymentMethod paymentMethodId;

    private boolean paymentStatus;

    private Date createTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "paymentId")
    @JsonBackReference
    private Transactions transactionId;

    public Payment(Orders orderId, PaymentMethod paymentMethodId, boolean paymentStatus, Date createTime) {
        this.orderId = orderId;
        this.paymentMethodId = paymentMethodId;
        this.paymentStatus = paymentStatus;
        this.createTime = createTime;
    }
}
