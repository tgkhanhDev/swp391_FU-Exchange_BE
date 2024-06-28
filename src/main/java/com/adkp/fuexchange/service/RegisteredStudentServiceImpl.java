package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrderPostProductDTO;
import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.mapper.OrderPostProductMapper;
import com.adkp.fuexchange.mapper.RegisteredStudentMapper;
import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.pojo.Payment;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Transactions;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.OrderDetailResponse;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisteredStudentServiceImpl implements RegisteredStudentService {

    public final RegisteredStudentRepository registeredStudentRepository;

    public final RegisteredStudentMapper registeredStudentMapper;

    private final PasswordEncoder passwordEncoder;

    private final OrderPostProductRepository orderPostProductRepository;

    private final OrderPostProductMapper orderPostProductMapper;

    private final OrdersRepository ordersRepository;

    private final PaymentRepository paymentRepository;

    private final TransactionsRepository transactionsRepository;

    @Autowired
    public RegisteredStudentServiceImpl(RegisteredStudentRepository registeredStudentRepository, RegisteredStudentMapper registeredStudentMapper, PasswordEncoder passwordEncoder, OrderPostProductRepository orderPostProductRepository, OrderPostProductMapper orderPostProductMapper, OrdersRepository ordersRepository, PaymentRepository paymentRepository, TransactionsRepository transactionsRepository) {
        this.registeredStudentRepository = registeredStudentRepository;
        this.registeredStudentMapper = registeredStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.orderPostProductRepository = orderPostProductRepository;
        this.orderPostProductMapper = orderPostProductMapper;
        this.ordersRepository = ordersRepository;
        this.paymentRepository = paymentRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public RegisteredStudentDTO viewProfile(Integer registeredStudentId) {
        return registeredStudentMapper.toRegisteredStudentDTO(
                registeredStudentRepository.getReferenceById(registeredStudentId)
        );
    }

    @Override
    @Transactional
    public ResponseObject<Object> updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        RegisteredStudent registeredStudentUpdate = registeredStudentRepository.getReferenceById(updatePasswordRequest.getIdWantUpdate());
        if (passwordEncoder.matches(updatePasswordRequest.getOldPassword(), registeredStudentUpdate.getPassword())) {
            if (updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmNewPassword())
            ) {
                registeredStudentUpdate.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
                registeredStudentRepository.save(registeredStudentUpdate);
                return ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .content("Thay đổi mật khẩu thành công!")
                        .build();
            }
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!")
                    .build();
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Mật khẩu cũ không chính xác!")
                .build();

    }

    @Override
    public OrderDetailResponse getOrdersDetailByRegisteredStudentId(Integer registeredStudentId, Integer orderId) {

        List<OrderPostProductDTO> orderPostProductList =
                orderPostProductMapper.toOrderPostProductDTOList(orderPostProductRepository.getOrdersDetailByRegisteredStudentId(registeredStudentId, orderId));

        List<OrderDetailResponse> postProductInOrder = getPostProductInOrder(orderPostProductList);

        return OrderDetailResponse.builder()
                .postProductInOrder(postProductInOrder)
                .build();
    }

    private List<OrderDetailResponse> getPostProductInOrder(List<OrderPostProductDTO> orderPostProductList) {

        List<OrderDetailResponse> postProductInOrder = new ArrayList<>();

        OrderPostProductDTO previousOrderProductDTO = null;

        for (OrderPostProductDTO currentOrderProductDTO : orderPostProductList) {

            currentOrderProductDTO.getPostProduct().setPriceBought(currentOrderProductDTO.getPriceBought() * 1000);

            if (previousOrderProductDTO != null) {

                if (currentOrderProductDTO.getSttOrder() == previousOrderProductDTO.getSttOrder() &&
                        currentOrderProductDTO.getVariationDetail().getVariationDetailId() != previousOrderProductDTO.getVariationDetail().getVariationDetailId()
                ) {

                    postProductInOrder.remove(previousOrderProductDTO);

                    postProductInOrder.add(
                            OrderDetailResponse.builder()
                                    .postProduct(currentOrderProductDTO.getPostProduct())
                                    .firstVariation(
                                            previousOrderProductDTO.getVariationDetail().getVariation().getVariationName() + ": "
                                                    + previousOrderProductDTO.getVariationDetail().getDescription()
                                    )
                                    .secondVariation(
                                            currentOrderProductDTO.getVariationDetail().getVariation().getVariationName() + ": "
                                                    + currentOrderProductDTO.getVariationDetail().getDescription()
                                    )
                                    .quantity(currentOrderProductDTO.getQuantity())
                                    .build());
                }
            } else {

                postProductInOrder.add(
                        OrderDetailResponse.builder()
                                .postProduct(currentOrderProductDTO.getPostProduct())
                                .firstVariation(
                                        currentOrderProductDTO.getVariationDetail().getVariation().getVariationName() + ": "
                                                + currentOrderProductDTO.getVariationDetail().getDescription()
                                )
                                .quantity(currentOrderProductDTO.getQuantity())
                                .build());
            }

            previousOrderProductDTO = currentOrderProductDTO;
        }

        return postProductInOrder;
    }

    private long totalPriceInOrder(Integer orderId) {

        Orders orders = ordersRepository.getReferenceById(orderId);

        Payment payment = paymentRepository.getReferenceById(orders.getPaymentId().getPaymentId());

        Transactions transactions = transactionsRepository.getReferenceById(payment.getTransactionId().getTransactionsId());

        return transactions.getTotalPrice() * 1000;
    }
}
