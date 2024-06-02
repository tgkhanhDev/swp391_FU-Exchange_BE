package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;
    public OrderServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public List<OrdersDTO> getOrderByRegisterId(Integer registeredStudentId) {
        return ordersMapper.toOrdersDTOList(ordersRepository.getOrderByRegisterId(registeredStudentId));
    }
}
