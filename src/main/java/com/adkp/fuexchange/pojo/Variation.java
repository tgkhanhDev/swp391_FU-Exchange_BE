package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Variation")
@ToString
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product productId;

    private String variationName;

    @OneToMany(mappedBy = "variationId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<VariationDetail> variationDetailId;

    public Variation(String variationName, Product productId) {
        this.variationName = variationName;
        this.productId = productId;
    }
}
