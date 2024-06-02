package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "orderStatusId", referencedColumnName = "orderStatusId")
    private OrderStatus orderStatusId;

    private LocalDate completeDate;

    private LocalDate createDate;

    private String description;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "orderId")
    @JsonBackReference
    private Payment paymentId;

    @OneToMany(mappedBy = "orderId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<OrderPostProduct> orderPostProductId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "orderId")
    @JsonBackReference
    private Review reviewId;

    public Orders(RegisteredStudent registeredStudentId, OrderStatus orderStatusId, LocalDate completeDate, LocalDate createDate, String description) {
        this.registeredStudentId = registeredStudentId;
        this.orderStatusId = orderStatusId;
        this.completeDate = completeDate;
        this.createDate = createDate;
        this.description = description;
    }

}
