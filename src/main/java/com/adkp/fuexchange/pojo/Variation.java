package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Variation")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product productId;

    private String variationName;

    public Variation(String variationName, Product productId) {
        this.variationName = variationName;
        this.productId = productId;
    }
}
