package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface PaymentService {

    ResponseObject<Object> paymentCod(OrdersRequest ordersRequest);

    ResponseObject<Object> paymentBanking(OrdersRequest ordersRequest);


}
