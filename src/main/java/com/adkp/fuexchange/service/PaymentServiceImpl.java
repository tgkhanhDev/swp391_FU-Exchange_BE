package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.request.PostProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    public PaymentServiceImpl(OrdersRepository ordersRepository, PaymentRepository paymentRepository, TransactionsRepository transactionsRepository, RegisteredStudentRepository registeredStudentRepository, OrdersStatusRepository ordersStatusRepository, PaymentMethodRepository paymentMethodRepository, OrderPostProductRepository orderPostProductRepository, PostProductRepository postProductRepository, VariationDetailRepository variationDetailRepository, TransactionsStatusRepository transactionsStatusRepository) {
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
    }

    @Override
    public ResponseObject<Object> paymentCod(OrdersRequest ordersRequest) {

        Orders ordersSaved = saveOrderAndOrderPostProduct(ordersRequest);

        savePaymentAndTransaction(ordersRequest, ordersSaved, false);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Mua hàng thành công!")
                .build();
    }

    @Override
    public ResponseObject<Object> paymentBanking(OrdersRequest ordersRequest) {

        Orders ordersSaved = saveOrderAndOrderPostProduct(ordersRequest);

        savePaymentAndTransaction(ordersRequest, ordersSaved, true);

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Mua hàng thành công!")
                .build();
    }

    private double totalPrice(List<PostProductRequest> postProductRequestList) {
        double totalPrice = 0;

        PostProductRequest previousProduct = null;
        for (PostProductRequest currentProduct : postProductRequestList) {
            if (previousProduct != null &&
                    currentProduct.getPostProductId() == previousProduct.getPostProductId() &&
                    currentProduct.getVariationDetailId() != previousProduct.getVariationDetailId()) {
                continue;
            } else {
                totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();
            }
            previousProduct = currentProduct;
        }
        return totalPrice;
    }

    private void savePaymentAndTransaction(OrdersRequest ordersRequest, Orders ordersSaved, boolean paymentStatus) {

        double totalPrice = totalPrice(ordersRequest.getPostProductToBuyRequests());

        Payment paymentSaved = paymentRepository.save(
                new Payment(
                        ordersSaved,
                        paymentMethodRepository.getReferenceById(ordersRequest.getPaymentMethodId()),
                        paymentStatus,
                        LocalDateTime.now()
                )
        );

        transactionsRepository.save(
                new Transactions(
                        paymentSaved,
                        transactionsStatusRepository.getReferenceById(3),
                        Double.parseDouble(new DecimalFormat("#.###").format(totalPrice / 1000)),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(3)
                )
        );
    }

    private Orders saveOrderAndOrderPostProduct(OrdersRequest ordersRequest) {
        Orders ordersSaved = ordersRepository.save(
                new Orders(
                        registeredStudentRepository.getReferenceById(ordersRequest.getRegisteredStudentId()),
                        ordersStatusRepository.getReferenceById(4),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(3),
                        ordersRequest.getDescription()
                )
        );
        for (PostProductRequest postProductRequest : ordersRequest.getPostProductToBuyRequests()) {
            orderPostProductRepository.save(
                    new OrderPostProduct(
                            new OrderPostProductEmbeddable(ordersSaved.getOrderId(), postProductRequest.getPostProductId(), postProductRequest.getVariationDetailId()),
                            ordersSaved,
                            postProductRepository.getReferenceById(postProductRequest.getPostProductId()),
                            variationDetailRepository.getReferenceById(postProductRequest.getVariationDetailId()),
                            postProductRequest.getQuantity(),
                            Double.parseDouble(new DecimalFormat("#.###").format(postProductRequest.getPrice() / 1000))
                    )
            );
        }
        return ordersSaved;
    }
}
