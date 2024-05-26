package com.adkp.fuexchange.pojo;

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

    private Date completeDate;

    private Date createDate;

    private String description;

    public Orders(RegisteredStudent registeredStudentId, OrderStatus orderStatusId, Date completeDate, Date createDate, String description) {
        this.registeredStudentId = registeredStudentId;
        this.orderStatusId = orderStatusId;
        this.completeDate = completeDate;
        this.createDate = createDate;
        this.description = description;
    }

}
