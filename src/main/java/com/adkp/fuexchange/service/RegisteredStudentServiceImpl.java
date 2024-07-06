package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrderPostProductDTO;
import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.mapper.OrderPostProductMapper;
import com.adkp.fuexchange.mapper.RegisteredStudentMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
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
import java.util.Optional;

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
    public OrderDetailResponse getOrdersDetailByRegisteredStudentId(Integer registeredStudentId) {

        List<OrderPostProductDTO> orderPostProductList =
                orderPostProductMapper.toOrderPostProductDTOList(orderPostProductRepository.getOrdersDetailByRegisteredStudentId(registeredStudentId));

        List<OrderDetailResponse> postProductInOrder = getPostProductInOrder(orderPostProductList);

        return OrderDetailResponse.builder()
                .postProductInOrder(postProductInOrder)
                .build();
    }

    @Override
    public RegisteredStudentDTO updateDeliveryAddress(Integer registeredStudentId, String deliveryAddress) {

        RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(registeredStudentId);

        registeredStudent.setDeliveryAddress(deliveryAddress);

        return registeredStudentMapper.toRegisteredStudentDTO(registeredStudentRepository.save(registeredStudent));
    }

    @Override
    public List<RegisteredStudentDTO> filterRegisteredStudent(String studentName) {

        String name = Optional.ofNullable(studentName).map(String::valueOf).orElse("");

        List<RegisteredStudent> registeredStudents = registeredStudentRepository.filterRegisteredStudent(name);

        return registeredStudentMapper.totoRegisteredStudentDTOList(registeredStudents);
    }

    @Override
    public RegisteredStudentDTO updateStatusRegisteredStudent(Integer registeredStudentId, Integer isActive) {
        RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(registeredStudentId);

        registeredStudent.setActive(isActive != 0);

        return registeredStudentMapper.toRegisteredStudentDTO(registeredStudentRepository.save(registeredStudent));
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
                                    .order(currentOrderProductDTO.getOrder())
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
                                    .imageUrlProduct(
                                            currentOrderProductDTO.getPostProduct().getProduct().getDetail().getProductImage().get(0).getImageUrl()
                                    )
                                    .build());
                }
            } else {

                postProductInOrder.add(
                        OrderDetailResponse.builder()
                                .order(currentOrderProductDTO.getOrder())
                                .postProduct(currentOrderProductDTO.getPostProduct())
                                .firstVariation(
                                        currentOrderProductDTO.getVariationDetail().getVariation().getVariationName() + ": "
                                                + currentOrderProductDTO.getVariationDetail().getDescription()
                                )
                                .quantity(currentOrderProductDTO.getQuantity())
                                .imageUrlProduct(
                                        currentOrderProductDTO.getPostProduct().getProduct().getDetail().getProductImage().get(0).getImageUrl()
                                )
                                .build());
            }
            currentOrderProductDTO.getPostProduct().getProduct().getDetail().setProductImage(null);
            previousOrderProductDTO = currentOrderProductDTO;
        }

        return postProductInOrder;
    }
}
