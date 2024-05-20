package com.adkp.fuexchange.dto;

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
@Table(name = "Campus")
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int campusId;

    private String campusName;

    @OneToMany(mappedBy = "postProductId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<PostProduct> postProductId;
    public Campus(String campusName) {
        this.campusName = campusName;
    }
}
