package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface OrderPostProductService {

    ResponseObject<Object>viewTotalPriceEachPostProductBySellerID(int sellerID);

}
