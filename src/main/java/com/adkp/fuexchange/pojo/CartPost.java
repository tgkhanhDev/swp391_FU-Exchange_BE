package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "CartPost")
@ToString
public class CartPost {

    @EmbeddedId
    private CartPostEmbeddable cartPostId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId("cartId")
    @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    private Cart cartId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId("postProductId")
    @JoinColumn(name = "postProductId", referencedColumnName = "postProductId")
    private PostProduct postProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId("variationDetailId")
    @JoinColumn(name = "variationDetailId", referencedColumnName = "variationDetailId")
    private VariationDetail variationDetailId;

    private int quantity;

    public CartPost(Cart cartId, PostProduct postProductId, VariationDetail variationDetailId, int quantity) {
        this.cartId = cartId;
        this.postProductId = postProductId;
        this.variationDetailId = variationDetailId;
        this.quantity = quantity;
    }
}
