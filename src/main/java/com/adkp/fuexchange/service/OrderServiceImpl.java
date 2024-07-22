package com.adkp.fuexchange.service;


import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.OrderUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final OrdersStatusRepository ordersStatusRepository;

    private final TransactionsRepository transactionsRepository;

    private final TransactionsStatusRepository transactionsStatusRepository;

    private final PaymentRepository paymentRepository;

    private final OrderPostProductRepository orderPostProductRepository;

    private final PostProductRepository postProductRepository;

    @Autowired
    public OrderServiceImpl(OrdersRepository ordersRepository, OrdersMapper ordersMapper, OrdersStatusRepository ordersStatusRepository, TransactionsRepository transactionsRepository, TransactionsStatusRepository transactionsStatusRepository, PaymentRepository paymentRepository, OrderPostProductRepository orderPostProductRepository, PostProductRepository postProductRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.ordersStatusRepository = ordersStatusRepository;
        this.transactionsRepository = transactionsRepository;
        this.transactionsStatusRepository = transactionsStatusRepository;
        this.paymentRepository = paymentRepository;
        this.orderPostProductRepository = orderPostProductRepository;
        this.postProductRepository = postProductRepository;
    }

    @Override
    @Transactional
    public OrdersDTO updateOrder(OrderUpdateRequest orderUpdateRequest) {

        Orders orders = ordersRepository.getReferenceById(orderUpdateRequest.getOrderId());

        updateQuantityForCancelOrder(orderUpdateRequest);

        orders.setOrderStatusId(ordersStatusRepository.getReferenceById(orderUpdateRequest.getOrderStatusId()));

        orders.setCompleteDate(LocalDateTime.now());

        updatePaymentStatus(orderUpdateRequest.getOrderStatusId(), orderUpdateRequest.getOrderId());

        updateStatusTransaction(orderUpdateRequest.getOrderStatusId(), orders.getPaymentId().getPaymentId());

        return ordersMapper.toOrdersDTO(orders);
    }

    private void updatePaymentStatus(int orderStatusId, int orderId) {

        Orders orders = ordersRepository.getReferenceById(orderId);
        if (orderStatusId == 4) {
            paymentRepository.getReferenceById(orders.getPaymentId().getPaymentId()).setPaymentStatus(false);
        }
    }

    private void updateStatusTransaction(int orderStatus, int paymentId) {
        Transactions transactions = transactionsRepository.getTransactionByPaymentId(paymentId);

        Payment payment = paymentRepository.getReferenceById(transactions.getPaymentId().getPaymentId());
        Map<Integer, Integer> statusMap = new HashMap<>();
        statusMap.put(2, 2);
        statusMap.put(3, 2);
        statusMap.put(4, 3);

        int statusId = statusMap.getOrDefault(orderStatus, 4);

        if (orderStatus == 5) {
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

    private void updateQuantityForCancelOrder(OrderUpdateRequest orderUpdateRequest) {
        List<OrderPostProduct> orderPostProducts = orderPostProductRepository.getOrderPostProductByOrderId(orderUpdateRequest.getOrderId());
        System.out.println(orderPostProducts.size());
        if (orderUpdateRequest.getOrderStatusId() == 4) {
            for (OrderPostProduct orderPostProduct : orderPostProducts) {
                PostProduct postProduct = postProductRepository.getReferenceById(orderPostProduct.getPostProductId().getPostProductId());
                postProduct.setQuantity(postProduct.getQuantity() + orderPostProduct.getQuantity());
            }
        }
    }
}
