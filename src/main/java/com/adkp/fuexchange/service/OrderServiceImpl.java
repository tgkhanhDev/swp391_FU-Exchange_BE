package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.repository.OrdersRepository;
import com.adkp.fuexchange.repository.OrdersStatusRepository;
import com.adkp.fuexchange.request.OrderUpdateRequest;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final OrdersStatusRepository ordersStatusRepository;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper, OrdersStatusRepository ordersStatusRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.ordersStatusRepository = ordersStatusRepository;
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

    @Override
    public OrdersDTO updateOrder(OrderUpdateRequest orderUpdateRequest) {

        if(
                orderUpdateRequest.getOrderStatusId() == 0 &&
                        !ordersRepository.existsById(orderUpdateRequest.getOrderId())
                && orderUpdateRequest.getCompleteDate() == null
        ){
            return null;
        }
        Orders orders = ordersRepository.getReferenceById(orderUpdateRequest.getOrderId());

        orders.setOrderStatusId(ordersStatusRepository.getReferenceById(orderUpdateRequest.getOrderStatusId()));


        return null;
    }

}
