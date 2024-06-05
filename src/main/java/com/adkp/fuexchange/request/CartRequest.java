package com.adkp.fuexchange.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    String studentId;
    int postProductId;
    int variationId;
    int quantity;
}
