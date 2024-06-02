package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrdersDTO;

import java.util.List;

public interface OrderService {
    List<OrdersDTO> getOrderByRegisterId(Integer registeredStudentId);
}
