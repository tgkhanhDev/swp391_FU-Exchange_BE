package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.mapper.RegisteredStudentMapper;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisteredStudentServiceImpl implements RegisteredStudentService {

    public final RegisteredStudentRepository registeredStudentRepository;

    public final RegisteredStudentMapper registeredStudentMapper;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegisteredStudentServiceImpl(RegisteredStudentRepository registeredStudentRepository, RegisteredStudentMapper registeredStudentMapper, PasswordEncoder passwordEncoder) {
        this.registeredStudentRepository = registeredStudentRepository;
        this.registeredStudentMapper = registeredStudentMapper;
        this.passwordEncoder = passwordEncoder;
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
        boolean isExist = registeredStudentRepository.existsById(updatePasswordRequest.getIdWantUpdate());
        if (isExist) {
            if (updatePasswordRequest.getPassword().equals(updatePasswordRequest.getConfirmPassword())) {
                RegisteredStudent registeredStudentUpdate = registeredStudentRepository.getReferenceById(updatePasswordRequest.getIdWantUpdate());
                registeredStudentUpdate.setPassword(passwordEncoder.encode(updatePasswordRequest.getPassword()));
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
                    .content("Mật khẩu không trùng khớp")
                    .build();
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin không tồn tại!")
                .build();

    }
}
