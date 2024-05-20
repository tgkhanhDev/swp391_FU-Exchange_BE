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
@Table(name = "ReportProductType")
public class ReportProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportProductTypeId;

    private String reportProductTypeName;

    private String description;

    @OneToMany(mappedBy = "reportProductId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<ReportProduct> reportProductId;

    public ReportProductType(String reportProductTypeName, String description) {
        this.reportProductTypeName = reportProductTypeName;
        this.description = description;
    }
}
