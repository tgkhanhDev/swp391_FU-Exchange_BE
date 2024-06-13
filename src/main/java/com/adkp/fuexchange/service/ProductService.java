package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.request.RegisterProductRequest;
import com.adkp.fuexchange.request.UpdateProductStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import java.util.List;


public interface ProductService {
    ResponseObject<Object> viewMoreProduct(int current);

    ResponseObject<Object> topProductByUserIdAndName(int sellerID, String productName, int current);

    ResponseObject<Object> getProductByProductID(int productID);

    ResponseObject<Object> createProduct(RegisterProductRequest registerProductRequest);

    ResponseObject<Object> updateProductInformation(UpdateInformationProductRequest updateInformationProductRequest);

    ResponseObject<Object> updateStatus(UpdateProductStatusRequest updateProductStatusRequest);

    long countTotalPostProduct();

    long countProduct(String name, List<ProductDTO> productDTOList);
}
