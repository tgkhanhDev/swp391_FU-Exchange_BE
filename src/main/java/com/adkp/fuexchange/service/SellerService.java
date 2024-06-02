package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface SellerService {

    ResponseObject<Object> viewInformationSellerById(int sellerId);

    ResponseObject<Object> registerToSeller(RegisterToSellerRequest registerToSellerRequest);

    ResponseObject<Object> updateInformationSeller(UpdateInformationSellerRequest updateInformationSellerRequest);

    ResponseObject<Object> updateStatusSeller(UpdateStatusRequest updateStatusRequest);
}
