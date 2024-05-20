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
@Table(name = "Variation")
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variationId;

    private String variationName;

    private String description;

    @OneToMany(mappedBy = "productId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Product> productId;

    public Variation(String variationName, String description) {
        this.variationName = variationName;
        this.description = description;
    }
}
