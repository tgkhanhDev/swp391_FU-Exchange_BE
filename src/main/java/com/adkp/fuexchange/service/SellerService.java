package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface SellerService {

    SellerDTO viewInformationSellerById(int sellerId);

    ResponseObject registerToSeller(RegisterToSellerRequest registerToSellerRequest);

    ResponseObject updateInformationSeller(UpdateInformationSellerRequest updateInformationSellerRequest);

    ResponseObject updateStatusSeller(UpdateStatusRequest updateStatusRequest);
}
