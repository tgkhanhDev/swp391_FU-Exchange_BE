package com.adkp.fuexchange.response;


import com.adkp.fuexchange.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class ProductResponse {
    private ResponseObject<Object> responseObject;

    private MetaResponse meta;

    private List<ProductDTO> data;
}