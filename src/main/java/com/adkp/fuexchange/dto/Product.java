package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "productDetailId", referencedColumnName = "productDetailId")
    private ProductDetail productDetailId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sellerId", referencedColumnName = "sellerId")
    private Seller sellerId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "variationId", referencedColumnName = "variationId")
    private Variation variationId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category categoryId;

    private double price;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "CartProduct",
            joinColumns = { @JoinColumn(name = "productId") },
            inverseJoinColumns = { @JoinColumn(name = "cartId") }
    )
    private List<Cart> cartId;
    public Product(ProductDetail productDetailId, Seller sellerId, Variation variationId, Category categoryId, double price) {
        this.productDetailId = productDetailId;
        this.sellerId = sellerId;
        this.variationId = variationId;
        this.categoryId = categoryId;
        this.price = price;
    }
}
