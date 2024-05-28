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
@Table(name = "ReportSellerType")
public class ReportSellerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSellerTypeId;

    private String reportTypeName;

    private String description;

    public ReportSellerType(String reportTypeName, String description) {
        this.reportTypeName = reportTypeName;
        this.description = description;
    }
}
