package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Cart;
import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.pojo.VariationDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
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
