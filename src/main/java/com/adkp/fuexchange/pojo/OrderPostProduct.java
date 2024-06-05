package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "OrderPostProduct")
public class OrderPostProduct {

    @EmbeddedId
    private CartPostEmbeddable orderPostProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("orderId")
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Orders orderId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("postProductId")
    @JoinColumn(name = "postProductId", referencedColumnName = "postProductId")
    private PostProduct postProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @MapsId("variationDetailId")
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
