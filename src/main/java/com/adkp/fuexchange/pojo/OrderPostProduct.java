package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "OrderPostProduct")
public class OrderPostProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderPostProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "postProductId", referencedColumnName = "postProductId")
    private PostProduct postProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "variationDetailId", referencedColumnName = "variationDetailId")
    private VariationDetail variationDetailId;

    private int quantity;

    private double priceBought;

    public OrderPostProduct(Orders orderId, PostProduct postProductId, VariationDetail variationDetailId, int quantity, double priceBought) {
        this.orderId = orderId;
        this.postProductId = postProductId;
        this.variationDetailId = variationDetailId;
        this.quantity = quantity;
        this.priceBought = priceBought;
    }
}
