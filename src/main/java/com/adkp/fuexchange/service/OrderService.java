package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.request.OrderUpdateRequest;

import java.util.List;

public interface OrderService {
    List<OrdersDTO> getOrderByRegisterId(Integer registeredStudentId);

    OrdersDTO updateOrder(OrderUpdateRequest orderUpdateRequest);

    OrdersDTO deleteOrder(Integer orderId);
}
