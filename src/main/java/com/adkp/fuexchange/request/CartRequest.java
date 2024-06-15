package com.adkp.fuexchange.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    String studentId;
    int postProductId;
    List<Integer> variationDetailId;
    int quantity;
}
