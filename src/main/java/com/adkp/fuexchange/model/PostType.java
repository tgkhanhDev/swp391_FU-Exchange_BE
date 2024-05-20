package com.adkp.fuexchange.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "PostType")
public class PostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postTypeId;

    private String postName;

    @OneToMany(mappedBy = "postProductId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<PostProduct> postProductId;

    public PostType(String postName) {
        this.postName = postName;
    }
}
