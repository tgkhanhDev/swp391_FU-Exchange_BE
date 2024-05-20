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
@Table(name = "ReportSeller")
public class ReportSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sellerId", referencedColumnName = "sellerId")
    private Seller sellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportSellerTypeId", referencedColumnName = "reportSellerTypeId")
    private ReportSellerType reportSellerTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "reportStatusId", referencedColumnName = "reportStatusId")
    private ReportStatus reportStatusId;

    private Date createDate;

    @Column(nullable = true)
    private String content;

    public ReportSeller(RegisteredStudent registeredStudentId, Seller sellerId, ReportSellerType reportSellerTypeId, ReportStatus reportStatusId, Date createDate, String content) {
        this.registeredStudentId = registeredStudentId;
        this.sellerId = sellerId;
        this.reportSellerTypeId = reportSellerTypeId;
        this.reportStatusId = reportStatusId;
        this.createDate = createDate;
        this.content = content;
    }
}
