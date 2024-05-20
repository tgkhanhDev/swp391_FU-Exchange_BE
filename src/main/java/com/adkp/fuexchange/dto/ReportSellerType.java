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
@Table(name = "ReportSellerType")
public class ReportSellerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSellerTypeId;

    private String reportSellerTypeName;

    private String description;

    @OneToMany(mappedBy = "reportSellerId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<ReportSeller> reportSellerId;

    public ReportSellerType(String reportSellerTypeName, String description) {
        this.reportSellerTypeName = reportSellerTypeName;
        this.description = description;
    }
}
