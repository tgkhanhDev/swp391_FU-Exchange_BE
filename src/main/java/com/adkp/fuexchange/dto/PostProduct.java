package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor(force = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "PostProduct")
public class PostProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postProductId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "postTypeId", referencedColumnName = "postTypeId")
    private PostType postTypeId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "campusId", referencedColumnName = "campusId")
    private Campus campusId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "postStatusId", referencedColumnName = "postStatusId")
    private PostStatus postStatusId;

    private int quantity;

    private Date createDate;

    private String content;

    @OneToMany(mappedBy = "reviewId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Review> reviewId;
    public PostProduct(Product productId, PostType postTypeId, Campus campusId, PostStatus postStatusId, int quantity, Date createDate) {
        this.productId = productId;
        this.postTypeId = postTypeId;
        this.campusId = campusId;
        this.postStatusId = postStatusId;
        this.quantity = quantity;
        this.createDate = createDate;
    }
}
