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
@Table(name = "Cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "registeredStudentId", referencedColumnName = "registeredStudentId")
    private RegisteredStudent registeredStudentId;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "CartProduct",
            joinColumns = { @JoinColumn(name = "cartId") },
            inverseJoinColumns = { @JoinColumn(name = "productId") }
    )
    private List<Product> productId;

    public Cart(RegisteredStudent registeredStudentId) {
        this.registeredStudentId = registeredStudentId;
    }
}
