package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.request.PostProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrdersRepository ordersRepository;

    private final PaymentRepository paymentRepository;

    private final TransactionsRepository transactionsRepository;

    private final RegisteredStudentRepository registeredStudentRepository;

    private final OrdersStatusRepository ordersStatusRepository;

    private final PaymentMethodRepository paymentMethodRepository;

    private final OrderPostProductRepository orderPostProductRepository;

    private final PostProductRepository postProductRepository;

    private final VariationDetailRepository variationDetailRepository;

    private final TransactionsStatusRepository transactionsStatusRepository;

    private final PostStatusRepository postStatusRepository;

    @Autowired
    public PaymentServiceImpl(OrdersRepository ordersRepository, PaymentRepository paymentRepository, TransactionsRepository transactionsRepository, RegisteredStudentRepository registeredStudentRepository, OrdersStatusRepository ordersStatusRepository, PaymentMethodRepository paymentMethodRepository, OrderPostProductRepository orderPostProductRepository, PostProductRepository postProductRepository, VariationDetailRepository variationDetailRepository, TransactionsStatusRepository transactionsStatusRepository, PostStatusRepository postStatusRepository) {
        this.ordersRepository = ordersRepository;
        this.paymentRepository = paymentRepository;
        this.transactionsRepository = transactionsRepository;
        this.registeredStudentRepository = registeredStudentRepository;
        this.ordersStatusRepository = ordersStatusRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderPostProductRepository = orderPostProductRepository;
        this.postProductRepository = postProductRepository;
        this.variationDetailRepository = variationDetailRepository;
        this.transactionsStatusRepository = transactionsStatusRepository;
        this.postStatusRepository = postStatusRepository;
    }

    @Override
    @Transactional(rollbackOn = {DataIntegrityViolationException.class})
    public ResponseObject<Object> payOrders(OrdersRequest ordersRequest) {
        boolean paymentStatus = false;

        long totalPrice = totalPrice(ordersRequest.getPostProductToBuyRequests());

        Orders ordersSaved = saveOrderAndOrderPostProduct(ordersRequest);

        if (ordersRequest.getPaymentMethodId() == 2) {

            paymentStatus = true;

        }

        savePaymentAndTransaction(ordersRequest.getPaymentMethodId(), ordersSaved, paymentStatus, totalPrice);

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Mua hàng thành công!")
                .build();
    }

    private long totalPrice(List<PostProductRequest> postProductRequestList) {
        long totalPrice = 0;

        Map<Integer, Integer> quantityEachPost = new HashMap<>();

        PostProductRequest previousProduct = null;
        for (PostProductRequest currentProduct : postProductRequestList) {
            if (previousProduct != null &&
                    currentProduct.getSttOrder() == previousProduct.getSttOrder()
            ) {
                if (currentProduct.getPostProductId() != previousProduct.getPostProductId()) {
                    quantityEachPost.merge(currentProduct.getPostProductId(), currentProduct.getQuantity(), Integer::sum);
                }
            } else {
                quantityEachPost.merge(currentProduct.getPostProductId(), currentProduct.getQuantity(), Integer::sum);
                totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();
            }
            previousProduct = currentProduct;
        }
        calcQuantityPostProduct(quantityEachPost);
        return totalPrice;
    }

    private void calcQuantityPostProduct(Map<Integer, Integer> quantityEachPost) {
        List<Integer> postProductId = new ArrayList<>();
        quantityEachPost.forEach((key, value) -> {
            postProductId.add(key);
        });

        List<PostProduct> postProductList = postProductRepository.findAllById(postProductId);

        for (PostProduct postProduct : postProductList) {
            int quantity = quantityEachPost.get(postProduct.getPostProductId());

            if ((postProduct.getQuantity() - quantity) < 0) {
                throw new DataIntegrityViolationException("Số lượng mà bạn muốn mua đã vượt quá giới hạn mà sản phẩm hiện có!");
            }

            if (postProduct.getQuantity() - quantity == 0) {
                postProduct.setPostStatusId(postStatusRepository.getReferenceById(1));
            }

            postProduct.setQuantity(postProduct.getQuantity() - quantity);
        }

        postProductRepository.saveAll(postProductList);
    }

    private void savePaymentAndTransaction(int getPaymentMethod, Orders ordersSaved, boolean paymentStatus, double totalPrice) {

        Payment paymentSaved = paymentRepository.save(
                new Payment(
                        ordersSaved,
                        paymentMethodRepository.getReferenceById(getPaymentMethod),
                        paymentStatus,
                        LocalDateTime.now()
                )
        );

        transactionsRepository.save(
                new Transactions(
                        paymentSaved,
                        transactionsStatusRepository.getReferenceById(1),
                        Long.parseLong(new DecimalFormat("#.###").format(totalPrice / 1000)),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(3)
                )
        );
    }

    private Orders saveOrderAndOrderPostProduct(OrdersRequest ordersRequest) {
        Orders ordersSaved = ordersRepository.save(
                new Orders(
                        registeredStudentRepository.getReferenceById(ordersRequest.getRegisteredStudentId()),
                        ordersStatusRepository.getReferenceById(1),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(3),
                        ordersRequest.getDescription()
                )
        );

        List<OrderPostProduct> orderPostProductList = new ArrayList<>();

        for (PostProductRequest postProductRequest : ordersRequest.getPostProductToBuyRequests()) {
            orderPostProductList.add(
                    OrderPostProduct.builder()
                            .sttOrder(postProductRequest.getSttOrder())
                            .orderId(ordersSaved)
                            .postProductId(postProductRepository.getReferenceById(postProductRequest.getPostProductId()))
                            .variationDetailId(variationDetailRepository.getReferenceById(postProductRequest.getVariationDetailId()))
                            .quantity(postProductRequest.getQuantity())
                            .priceBought(Long.parseLong(new DecimalFormat("#.###").format(postProductRequest.getPrice() / 1000)))
                            .build()
            );
        }

        orderPostProductRepository.saveAll(orderPostProductList);

        return ordersSaved;
    }
}
