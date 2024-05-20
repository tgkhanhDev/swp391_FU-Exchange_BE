package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ReportProduct")
public class ReportProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sellerId", referencedColumnName = "sellerId")
    private Seller sellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportProductTypeId", referencedColumnName = "reportProductTypeId")
    private ReportProductType reportProductTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportStatusId", referencedColumnName = "reportStatusId")
    private ReportStatus reportStatusId;

    private Date createDate;

    @Column(nullable = true)
    private String content;

    public ReportProduct(RegisteredStudent registeredStudentId, Seller sellerId, ReportProductType reportProductTypeId, ReportStatus reportStatusId, Date createDate, String content) {
        this.registeredStudentId = registeredStudentId;
        this.sellerId = sellerId;
        this.reportProductTypeId = reportProductTypeId;
        this.reportStatusId = reportStatusId;
        this.createDate = createDate;
        this.content = content;
    }
}
