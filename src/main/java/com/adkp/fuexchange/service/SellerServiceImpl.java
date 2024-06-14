package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.mapper.SellerMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Roles;
import com.adkp.fuexchange.pojo.Seller;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.SellerRepository;
import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (registeredStudentRepository.existsById(registerToSellerRequest.getRegisteredStudentId())) {
            RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(registerToSellerRequest.getRegisteredStudentId());
            if (passwordEncoder.matches(
                    registerToSellerRequest.getPassword(),
                    registeredStudent.getPassword()
            )) {
                registeredStudent.setRoleId(new Roles("Seller"));
                registeredStudentRepository.save(registeredStudent);
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
        if (sellerRepository.existsById(updateStatusRequest.getSellerId())) {
            Seller seller = sellerRepository.getReferenceById(updateStatusRequest.getSellerId());
            seller.setActive(updateStatusRequest.getIsActive());
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
    public SellerDTO getInformationSellerByStudentId(String studentId) {

        return sellerMapper.toSellerDTO(
                sellerRepository.getInformationSellerByStudentId(studentId)
        );
    }

    @Override
    public void deleteSellerByID(int sellerID) {
        sellerRepository.deleteById(sellerID);
    }
}
