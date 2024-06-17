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
import java.util.*;

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
    @Transactional(rollbackOn = {DataIntegrityViolationException.class})
    public ResponseObject<Object> payOrders(OrdersRequest ordersRequest) {
        boolean paymentStatus = false;
        if (registeredStudentRepository.getReferenceById(ordersRequest.getRegisteredStudentId()).getDeliveryAddress() == null) {
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Chưa có địa chỉ nhận hàng. Vui lòng điền đầy đủ thông tin trước khi mua hàng!")
                    .build();
        }

        double totalPrice = totalPrice(ordersRequest.getPostProductToBuyRequests());

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

    private double totalPrice(List<PostProductRequest> postProductRequestList) {
        double totalPrice = 0;

        postProductRequestList.sort(new Comparator<PostProductRequest>() {
            @Override
            public int compare(PostProductRequest p1, PostProductRequest p2) {
                return Integer.compare(p1.getVariationId(), p2.getVariationId());
            }
        });

        Map<Integer, Integer> quantityEachPost = new HashMap<>();

        PostProductRequest previousProduct = null;
        for (PostProductRequest currentProduct : postProductRequestList) {
            if (previousProduct != null &&
                    currentProduct.getPostProductId() == previousProduct.getPostProductId()) {
                if (currentProduct.getVariationId() == previousProduct.getVariationId()) {
                    quantityEachPost.merge(currentProduct.getPostProductId(), currentProduct.getQuantity(), Integer::sum);
                    totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();
                }
                currentProduct = previousProduct;
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

        List<PostProduct> postProductList = postProductRepository.getListPostProductById(postProductId);
        for (PostProduct postProduct : postProductList) {
            int quantity = quantityEachPost.get(postProduct.getPostProductId());
            if ((postProduct.getQuantity() - quantity) < 0) {
                throw new DataIntegrityViolationException("Số lượng mà bạn muốn mua đã vượt quá giới hạn mà sản phẩm hiện có!");
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
                        ordersStatusRepository.getReferenceById(1),
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
                            Double.parseDouble(new DecimalFormat("#.###").format(postProductRequest.getPrice() / 1000)),
                            false
                    )
            );
        }
        return ordersSaved;
    }
}
