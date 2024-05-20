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
@Table(name = "PaymentMethod")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentMethodId;

    private String paymentMethodName;

    @OneToMany(mappedBy = "paymentId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Payment> paymentId;

    public PaymentMethod(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName;
    }
}
