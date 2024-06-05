package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "VariationDetail")
public class VariationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variationDetailId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "variationId", referencedColumnName = "variationId")
    private Variation variationId;

    private String description;

    public VariationDetail(Variation variationId, String description) {
        this.variationId = variationId;
        this.description = description;
    }
}
