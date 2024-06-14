package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.VariationDetailRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface VariationDetailService {
    ResponseObject<Object> CreateNewVariationByVariationID(VariationDetailRequest variationDetailRequest);
    void deleteVariationDetailByID(int variationDetailId);
}
