package com.adkp.fuexchange.model;

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
    @JoinColumn(name = "buyerId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent buyerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportProductTypeId", referencedColumnName = "reportProductTypeId")
    private ReportProductType reportProductTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportStatusId", referencedColumnName = "reportStatusId")
    private ReportStatus reportStatusId;

    private Date createTime;

    @Column(nullable = true)
    private String content;

    public ReportProduct(RegisteredStudent buyerId, Product productId, ReportProductType reportProductTypeId, ReportStatus reportStatusId, Date createTime, String content) {
        this.buyerId = buyerId;
        this.productId = productId;
        this.reportProductTypeId = reportProductTypeId;
        this.reportStatusId = reportStatusId;
        this.createTime = createTime;
        this.content = content;
    }
}
