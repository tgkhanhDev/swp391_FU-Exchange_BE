package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface PaymentService {

    ResponseObject<Object> payOrders(OrdersRequest ordersRequest);

}
