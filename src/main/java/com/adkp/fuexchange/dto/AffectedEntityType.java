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
@Table(name = "AffectedEntityType")
public class AffectedEntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int affectedEntityTypeId;

    private String affectedEntityTypeName;

//    @OneToMany(mappedBy = "activityId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonBackReference
//    private List<Activity> activityId;

    public AffectedEntityType(String affectedEntityTypeName) {
        this.affectedEntityTypeName = affectedEntityTypeName;
    }
}
