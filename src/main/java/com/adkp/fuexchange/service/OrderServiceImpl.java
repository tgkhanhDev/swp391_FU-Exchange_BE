package com.adkp.fuexchange.service;


import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.pojo.Payment;
import com.adkp.fuexchange.pojo.Transactions;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.OrderUpdateRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final OrdersStatusRepository ordersStatusRepository;

    private final TransactionsRepository transactionsRepository;

    private final TransactionsStatusRepository transactionsStatusRepository;

    private final PaymentRepository paymentRepository;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper, OrdersStatusRepository ordersStatusRepository, TransactionsRepository transactionsRepository, TransactionsStatusRepository transactionsStatusRepository, PaymentRepository paymentRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.ordersStatusRepository = ordersStatusRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionsStatusRepository = transactionsStatusRepository;
        this.paymentRepository = paymentRepository;
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
    @Transactional
    public OrdersDTO updateOrder(OrderUpdateRequest orderUpdateRequest) {

        if (
                !ordersRepository.existsById(orderUpdateRequest.getOrderId())
        ) {
            return null;
        }
        Orders orders = ordersRepository.getReferenceById(orderUpdateRequest.getOrderId());

        orders.setOrderStatusId(ordersStatusRepository.getReferenceById(orderUpdateRequest.getOrderStatusId()));

        if (orderUpdateRequest.getCompleteDate() != null && orderUpdateRequest.getOrderStatusId() == 1) {
            orders.setCompleteDate(orderUpdateRequest.getCompleteDate());
        }

        orders.setDescription(orderUpdateRequest.getDescription());
        updateStatusTransaction(orderUpdateRequest.getOrderStatusId(), orders.getPaymentId().getPaymentId());
        return ordersMapper.toOrdersDTO(orders);
    }

    private void updateStatusTransaction(int orderStatus, int paymentId) {
        Transactions transactions = transactionsRepository.getTransactionByPaymentId(paymentId);

        Payment payment = paymentRepository.getReferenceById(transactions.getPaymentId().getPaymentId());
        Map<Integer, Integer> statusMap = new HashMap<>();
        statusMap.put(2, 2);
        statusMap.put(3, 2);
        statusMap.put(4, 3);

        int statusId = statusMap.getOrDefault(orderStatus, 4);

        if(orderStatus == 5){
            payment.setPaymentStatus(true);
        }

        transactions.setTransactionsStatusId(transactionsStatusRepository.getReferenceById(statusId));
    }

    @Override
    @Transactional
    public OrdersDTO deleteOrder(Integer orderId) {
        Orders orderDeleted = ordersRepository.findById(orderId)
                .orElseThrow(() -> null);
        if (orderDeleted == null) {
            return null;
        }
        ordersRepository.delete(orderDeleted);
        return ordersMapper.toOrdersDTO(orderDeleted);
    }
}
