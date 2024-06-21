package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartPostDTO {

    private CartPostEmbeddable cartPostId;

    private CartDTO cart;

    private PostProductDTO postProduct;

    private VariationDetailDTO variationDetail;

    private int quantity;
}
