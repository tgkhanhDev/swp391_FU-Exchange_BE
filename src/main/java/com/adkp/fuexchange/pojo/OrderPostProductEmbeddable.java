package com.adkp.fuexchange.pojo;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class OrderPostProductEmbeddable {
    private int orderId;

    private int postProductId;

    private int variationDetailId;
}
