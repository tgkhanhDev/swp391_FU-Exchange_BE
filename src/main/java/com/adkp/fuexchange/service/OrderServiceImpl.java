package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.repository.OrdersRepository;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    public OrderServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public ResponseObject<Object> getOrderByRegisterId(Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .message("Xem thông tin thành công")
                .data(ordersMapper.toOrdersDTOList(ordersRepository.getOrderByRegisterId(registeredStudentId)))
                .build();
    }

}
