package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.request.OrdersRequest;

public interface PaymentService {

    Orders payOrders(OrdersRequest ordersRequest);

}
