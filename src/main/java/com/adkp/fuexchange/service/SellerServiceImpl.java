package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.OrderPostProductDTO;
import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.dto.VariationDetailDTO;
import com.adkp.fuexchange.mapper.OrderPostProductMapper;
import com.adkp.fuexchange.mapper.OrdersMapper;
import com.adkp.fuexchange.mapper.SellerMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Seller;
import com.adkp.fuexchange.repository.OrderPostProductRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.RoleRepository;
import com.adkp.fuexchange.repository.SellerRepository;
import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
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
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    private final RegisteredStudentRepository registeredStudentRepository;

    private final PasswordEncoder passwordEncoder;

    private final OrderPostProductRepository orderPostProductRepository;

    private final OrdersMapper ordersMapper;

    private final OrderPostProductMapper orderPostProductMapper;

    private final RoleRepository roleRepository;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper, RegisteredStudentRepository registeredStudentRepository, PasswordEncoder passwordEncoder, OrderPostProductRepository orderPostProductRepository, OrdersMapper ordersMapper, OrderPostProductMapper orderPostProductMapper, RoleRepository roleRepository) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.registeredStudentRepository = registeredStudentRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderPostProductRepository = orderPostProductRepository;
        this.ordersMapper = ordersMapper;
        this.orderPostProductMapper = orderPostProductMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseObject<Object> viewInformationSellerById(int sellerId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(sellerMapper.toSellerDTO(
                        sellerRepository.getReferenceById(sellerId)
                ))
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> registerToSeller(RegisterToSellerRequest registerToSellerRequest) {

        String content = "Mật khẩu không chính xác!";

        int status = HttpStatus.BAD_REQUEST.value();

        String message = HttpStatus.BAD_REQUEST.name();

        Object data = null;

        RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(registerToSellerRequest.getRegisteredStudentId());


        if (passwordEncoder.matches(
                registerToSellerRequest.getPassword(),
                registeredStudent.getPassword()
        )) {

            Seller seller = sellerRepository.getInformationSellerByStudentId(registeredStudent.getStudentId().getStudentId());

            if (seller != null && seller.getIsActive() == 2) {
                content = "Tài khoản của bạn đã đăng ký trở thành người bán nhưng chưa được xác nhận!";
            } else if (seller == null) {

                registeredStudent.setRoleId(roleRepository.getReferenceById(2));

                Seller sellerSaved = sellerRepository.save(
                        Seller.builder()
                                .registeredStudentId(registeredStudent)
                                .bankingName(registerToSellerRequest.getBankingName())
                                .bankingNumber(registerToSellerRequest.getBankingNumber())
                                .isActive(2)
                                .build()
                );
                status = HttpStatus.OK.value();

                message = HttpStatus.OK.name();

                content = "Đăng ký thành công. Xin chờ xác nhận!";

                data = sellerMapper.toSellerDTO(sellerSaved);
            } else {
                content = "Tài khoản của bạn đã đăng ký trở thành người bán!";
            }
        }

        return ResponseObject.builder()
                .status(status)
                .message(message)
                .content(content)
                .data(data)
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> updateInformationSeller(UpdateInformationSellerRequest updateInformationSellerRequest) {
        if (sellerRepository.existsById(updateInformationSellerRequest.getSellerId())) {
            Seller seller = sellerRepository.getReferenceById(updateInformationSellerRequest.getSellerId());
            seller.setBankingNumber(updateInformationSellerRequest.getBankingNumber());
            seller.setBankingName(updateInformationSellerRequest.getBankingName());
            sellerRepository.save(seller);
            return ResponseObject.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.name())
                    .content("Cập nhật thông tin thành công!")
                    .build();
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin người dùng không chính xác!")
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> updateStatusSeller(UpdateStatusRequest updateStatusRequest) {

        Seller seller = sellerRepository.getReferenceById(updateStatusRequest.getSellerId());

        seller.setIsActive(updateStatusRequest.getIsActive());

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật thông tin thành công!")
                .build();

    }

    @Override
    public SellerDTO getInformationSellerByStudentId(String studentId) {

        return sellerMapper.toSellerDTO(
                sellerRepository.getInformationSellerByStudentId(studentId)
        );
    }

    @Override
    public void deleteSellerByID(int sellerID) {
        sellerRepository.deleteById(sellerID);
    }

    @Override
    public List<OrdersDTO> getOrderBySellerId(Integer sellerId) {
        return ordersMapper.toOrdersDTOList(orderPostProductRepository.getOrdersBySellerId(sellerId));
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailBySellerIdAndOrderId(Integer sellerId, Integer orderId) {

        List<OrderPostProductDTO> orderPostProductList =
                orderPostProductMapper.toOrderPostProductDTOList(orderPostProductRepository.getOrdersDetailBySellerIdAndOrderId(sellerId, orderId));

        OrderPostProductDTO previousOrderPostProduct = null;

        List<OrderDetailResponse> orderDetailResponse = new ArrayList<>();

        List<VariationDetailDTO> variationDetailDTO = new ArrayList<>();
        for (OrderPostProductDTO currentOrderPostProduct : orderPostProductList) {

            variationDetailDTO.add(currentOrderPostProduct.getVariationDetail());

            if (previousOrderPostProduct != null &&
                    currentOrderPostProduct.getOrder().getOrderId() == previousOrderPostProduct.getOrder().getOrderId() &&
                    currentOrderPostProduct.getPostProduct().getPostProductId() == previousOrderPostProduct.getPostProduct().getPostProductId() &&
                    currentOrderPostProduct.getVariationDetail().getVariationDetailId() != previousOrderPostProduct.getVariationDetail().getVariationDetailId()
            ) {
                variationDetailDTO.clear();

                variationDetailDTO.add(currentOrderPostProduct.getVariationDetail());

                variationDetailDTO.add(previousOrderPostProduct.getVariationDetail());
                continue;
            } else if (previousOrderPostProduct != null &&
                    currentOrderPostProduct.getOrder().getOrderId() != previousOrderPostProduct.getOrder().getOrderId() &&
                    currentOrderPostProduct.getPostProduct().getPostProductId() != previousOrderPostProduct.getPostProduct().getPostProductId()
            ) {
                variationDetailDTO.clear();
                variationDetailDTO.add(currentOrderPostProduct.getVariationDetail());
            }

            orderDetailResponse.add(OrderDetailResponse.builder()
                    .order(currentOrderPostProduct.getOrder())
                    .postProduct(currentOrderPostProduct.getPostProduct())
                    .priceBought(currentOrderPostProduct.getPriceBought())
                    .quantity(currentOrderPostProduct.getQuantity())
                    .orderPostProductStatus(currentOrderPostProduct.isOrderPostProductStatus())
                    .variationDetail(variationDetailDTO)
                    .build());

            previousOrderPostProduct = currentOrderPostProduct;

        }

        return orderDetailResponse;
    }
}
