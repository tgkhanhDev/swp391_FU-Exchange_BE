package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.PostProduct;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPostProductResponse {

    private int postProductID;

  private long totalpriceBought;
    private PostProductResponse PostProductResponse;
}
