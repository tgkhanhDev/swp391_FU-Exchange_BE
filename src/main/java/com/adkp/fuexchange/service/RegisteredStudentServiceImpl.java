package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrderPostProductDTO;
import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.dto.VariationDetailDTO;
import com.adkp.fuexchange.mapper.OrderPostProductMapper;
import com.adkp.fuexchange.mapper.RegisteredStudentMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.repository.OrderPostProductRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.OrderDetailResponse;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisteredStudentServiceImpl implements RegisteredStudentService {

    public final RegisteredStudentRepository registeredStudentRepository;

    public final RegisteredStudentMapper registeredStudentMapper;

    private final PasswordEncoder passwordEncoder;

    private final OrderPostProductRepository orderPostProductRepository;

    private final OrderPostProductMapper orderPostProductMapper;

    @Autowired
    public RegisteredStudentServiceImpl(RegisteredStudentRepository registeredStudentRepository, RegisteredStudentMapper registeredStudentMapper, PasswordEncoder passwordEncoder, OrderPostProductRepository orderPostProductRepository, OrderPostProductMapper orderPostProductMapper) {
        this.registeredStudentRepository = registeredStudentRepository;
        this.registeredStudentMapper = registeredStudentMapper;
        this.passwordEncoder = passwordEncoder;
        this.orderPostProductRepository = orderPostProductRepository;
        this.orderPostProductMapper = orderPostProductMapper;
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
    public List<OrderDetailResponse> getOrdersDetailByRegisteredStudentId(Integer registeredStudentId, Integer orderStatusId) {

        List<OrderPostProductDTO> orderPostProductList =
                orderPostProductMapper.toOrderPostProductDTOList(orderPostProductRepository.getOrdersDetailByRegisteredStudentId(registeredStudentId, orderStatusId));

        OrderPostProductDTO previousOrderPostProduct = null;

        List<OrderDetailResponse> orderDetailResponse = new ArrayList<>();

        List<VariationDetailDTO> variationDetailDTOList = new ArrayList<>();

        Map<Integer, List<VariationDetailDTO>> mapPostToVariationDetail = new HashMap<>();

        for (OrderPostProductDTO currentOrderPostProduct : orderPostProductList) {

            currentOrderPostProduct.getVariationDetail().getVariation().setVariationDetail(null);

            if (previousOrderPostProduct != null &&
                    currentOrderPostProduct.getOrder().getOrderId() == previousOrderPostProduct.getOrder().getOrderId() &&
                    currentOrderPostProduct.getPostProduct().getPostProductId() == previousOrderPostProduct.getPostProduct().getPostProductId() &&
                    currentOrderPostProduct.getVariationDetail().getVariationDetailId() != previousOrderPostProduct.getVariationDetail().getVariationDetailId()
            ) {

                mapPostToVariationDetail.get(currentOrderPostProduct.getPostProduct().getPostProductId()).add(currentOrderPostProduct.getVariationDetail());

                continue;
            } else if (
                    previousOrderPostProduct != null &&
                            currentOrderPostProduct.getOrder().getOrderId() != previousOrderPostProduct.getOrder().getOrderId() &&
                            currentOrderPostProduct.getPostProduct().getPostProductId() != previousOrderPostProduct.getPostProduct().getPostProductId()
            ) {

                mapPostToVariationDetail.get(previousOrderPostProduct.getPostProduct().getPostProductId()).add(currentOrderPostProduct.getVariationDetail());

            } else {

                mapPostToVariationDetail.put(currentOrderPostProduct.getPostProduct().getPostProductId(), new ArrayList<>());

                mapPostToVariationDetail.get(currentOrderPostProduct.getPostProduct().getPostProductId()).add(currentOrderPostProduct.getVariationDetail());
            }

            if (mapPostToVariationDetail.containsKey(currentOrderPostProduct.getPostProduct().getPostProductId())) {
                variationDetailDTOList = mapPostToVariationDetail.get(currentOrderPostProduct.getPostProduct().getPostProductId());
            }

            orderDetailResponse.add(OrderDetailResponse.builder()
                    .order(currentOrderPostProduct.getOrder())
                    .postProduct(currentOrderPostProduct.getPostProduct())
                    .priceBought(currentOrderPostProduct.getPriceBought() * 1000)
                    .quantity(currentOrderPostProduct.getQuantity())
                    .variationDetail(variationDetailDTOList)
                    .build());

            previousOrderPostProduct = currentOrderPostProduct;

        }

        return orderDetailResponse;
    }
}
