package com.adkp.fuexchange.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
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

    public PostProduct(Product productId, PostType postTypeId, Campus campusId, PostStatus postStatusId, int quantity, Date createDate) {
        this.productId = productId;
        this.postTypeId = postTypeId;
        this.campusId = campusId;
        this.postStatusId = postStatusId;
        this.quantity = quantity;
        this.createDate = createDate;
    }
}
