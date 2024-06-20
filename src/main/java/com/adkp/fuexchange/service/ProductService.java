package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import com.adkp.fuexchange.response.ProductResponse;
import com.adkp.fuexchange.response.ResponseObject;

import java.util.List;


public interface ProductService {
    ResponseObject<Object> viewMoreProduct(int current);

    ResponseObject<Object> topProductByUserIdAndName(int sellerID, String productName, int current);

    ResponseObject<Object> getProductByProductID(int productID);

    ResponseObject<Object> updateProductInformation(UpdateInformationProductRequest updateInformationProductRequest);

    long countTotalPostProduct();

    long countProduct(String name, List<ProductDTO> productDTOList);

    ProductResponse getProductByVariationDetailId(List<Integer> variationDetailId);
}
