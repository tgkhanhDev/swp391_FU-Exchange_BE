package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.request.OrderUpdateRequest;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface OrderService {
    ResponseObject<Object> getOrderByRegisterId(Integer registeredStudentId);

    OrdersDTO updateOrder(OrderUpdateRequest orderUpdateRequest);
}
