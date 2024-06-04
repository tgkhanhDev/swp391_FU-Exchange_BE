package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class PostProductRequest {

    private int postProductId;

    private int variationDetailId;

    private int quantity;

    private double price;
}
