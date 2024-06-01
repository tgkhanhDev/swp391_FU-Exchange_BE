package com.adkp.fuexchange.pojo;

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
    private int cartId;
    private int postProductId;
}
