package com.adkp.fuexchange.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class CartPostEmbeddable implements Serializable {
    private int orderId;
    private int productId;
}
