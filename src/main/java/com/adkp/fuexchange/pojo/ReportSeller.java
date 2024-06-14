package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ReportSeller")
public class ReportSeller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportSellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "buyerId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent buyerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "sellerId", referencedColumnName = "sellerId")
    private Seller sellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "reportSellerTypeId", referencedColumnName = "reportSellerTypeId")
    private ReportSellerType reportSellerTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "reportStatusId", referencedColumnName = "reportStatusId")
    private ReportStatus reportStatusId;

    private LocalDate createTime;

    @Column(nullable = true)
    private String content;

    public ReportSeller(RegisteredStudent buyerId, Seller sellerId, ReportSellerType reportSellerTypeId, ReportStatus reportStatusId, LocalDate createTime, String content) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.reportSellerTypeId = reportSellerTypeId;
        this.reportStatusId = reportStatusId;
        this.createTime = createTime;
        this.content = content;
    }
}
