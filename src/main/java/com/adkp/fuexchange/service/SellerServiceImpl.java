package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.mapper.SellerMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Seller;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.SellerRepository;
import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    private final RegisteredStudentRepository registeredStudentRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper, RegisteredStudentRepository registeredStudentRepository, PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.registeredStudentRepository = registeredStudentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SellerDTO viewInformationSellerById(int sellerId) {
        return sellerMapper.toSellerDTO(
                sellerRepository.getReferenceById(sellerId)
        );
    }

    @Override
    @Transactional
    public ResponseObject registerToSeller(RegisterToSellerRequest registerToSellerRequest) {
        if (sellerRepository.existsById(registerToSellerRequest.getRegisteredStudentId())) {
            RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(registerToSellerRequest.getRegisteredStudentId());
            if (passwordEncoder.matches(
                    passwordEncoder.encode(registerToSellerRequest.getPassword()),
                    registeredStudent.getPassword()
            )) {
                try {
                    sellerRepository.save(new Seller(
                            registeredStudent,
                            registerToSellerRequest.getBankingNumber(),
                            registerToSellerRequest.getBankingName(),
                            false
                    ));
                    return ResponseObject.builder()
                            .status(HttpStatus.ACCEPTED.value())
                            .message(HttpStatus.ACCEPTED.name())
                            .content("Đăng ký thành công! Xin chờ xác nhận.")
                            .build();
                } catch (DataAccessException dataAccessException) {
                    return ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .content("Lỗi trong quá trình lưu trữ dữ liệu!")
                            .build();
                } catch (Exception exception) {
                    return ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                            .content("Lỗi không xác định!")
                            .build();
                }
            }
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin người dùng không chính xác!")
                .build();
    }

    @Override
    @Transactional
    public ResponseObject updateInformationSeller(UpdateInformationSellerRequest updateInformationSellerRequest) {
        if (sellerRepository.existsById(updateInformationSellerRequest.getSellerId())) {
            try {
                Seller seller = sellerRepository.getReferenceById(updateInformationSellerRequest.getSellerId());
                seller.setBankingNumber(updateInformationSellerRequest.getBankingNumber());
                seller.setBankingName(updateInformationSellerRequest.getBankingName());
                sellerRepository.save(seller);
                return ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .content("Cập nhật thông tin thành công!")
                        .build();
            } catch (DataAccessException dataAccessException) {
                return ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .content("Lỗi trong quá trình lưu trữ dữ liệu!")
                        .build();
            } catch (Exception exception) {
                return ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .content("Lỗi không xác định!")
                        .build();
            }
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin người dùng không chính xác!")
                .build();
    }

    @Override
    @Transactional
    public ResponseObject updateStatusSeller(UpdateStatusRequest updateStatusRequest) {
        if (sellerRepository.existsById(updateStatusRequest.getSellerId())) {
            try {
                Seller seller = sellerRepository.getReferenceById(updateStatusRequest.getSellerId());
                seller.setActive(updateStatusRequest.getIsActive());
                sellerRepository.save(seller);
                return ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .content("Cập nhật thông tin thành công!")
                        .build();
            } catch (DataAccessException dataAccessException) {
                return ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .content("Lỗi trong quá trình lưu trữ dữ liệu!")
                        .build();
            } catch (Exception exception) {
                return ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .content("Lỗi không xác định!")
                        .build();
            }
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin người dùng không chính xác!")
                .build();
    }
}
