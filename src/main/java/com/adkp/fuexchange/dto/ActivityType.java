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
@Table(name = "ActivityType")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int activityTypeId;

    private String activityTypeName;

//    @OneToMany(mappedBy = "productId", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JsonBackReference
//    private List<Activity> activityId;

    public ActivityType(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }
}
