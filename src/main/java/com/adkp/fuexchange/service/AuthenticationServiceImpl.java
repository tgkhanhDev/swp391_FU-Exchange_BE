package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.Cart;
import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Student;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.request.StaffLoginRequest;
import com.adkp.fuexchange.response.InforLoginResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.StaffInformationLoginResponse;
import com.adkp.fuexchange.security.RegisteredStudentDetailService;
import com.adkp.fuexchange.security.StaffDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RegisteredStudentDetailService registeredStudentDetailService;
    private final StaffDetailService staffDetailService;
    private final RegisteredStudentRepository registeredStudentRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public AuthenticationServiceImpl(RegisteredStudentDetailService registeredStudentDetailService, StaffDetailService staffDetailService, RegisteredStudentRepository registeredStudentRepository, AuthenticationManager authenticationManager, StudentRepository studentRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, CartRepository cartRepository, StaffRepository staffRepository) {
        this.registeredStudentDetailService = registeredStudentDetailService;
        this.staffDetailService = staffDetailService;
        this.registeredStudentRepository = registeredStudentRepository;
        this.authenticationManager = authenticationManager;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.cartRepository = cartRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public ResponseObject<Object> login(LoginRequest loginRequest) {
        UserDetails registeredStudent = registeredStudentDetailService.loadUserByUsername(loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), registeredStudent.getPassword())) {
            return ResponseObject.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(HttpStatus.UNAUTHORIZED.name().toLowerCase())
                    .content("Sai tài khoản hoặc mật khẩu")
                    .build();
        } else if (!registeredStudent.isAccountNonLocked()) {
            return ResponseObject.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(HttpStatus.UNAUTHORIZED.name().toLowerCase())
                    .content("Tài khoản bị vô hiệu hóa")
                    .build();
        }
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                loginRequest.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name().toLowerCase())
                .content("Đăng nhập thành công")
                .data(InforLoginResponse
                        .builder()
                        .username(registeredStudent.getUsername())
                        .registeredStudentId(registeredStudentRepository.findRegisteredStudentByStudentId(registeredStudent.getUsername()).getRegisteredStudentId())
                        .role(registeredStudent.getAuthorities().toArray()[0].toString())
                        .accessToken("123")
                        .build())
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name().toLowerCase())
                    .content("Mật khẩu xác nhận không trùng khớp!")
                    .build();
        }
        RegisteredStudent rs = registeredStudentRepository.save(
                RegisteredStudent.builder()
                        .studentId(studentRepository.getReferenceById(registerRequest.getStudentId()))
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .roleId(roleRepository.getReferenceById(1))
                        .active(true)
                        .build()
        );
        //Đăng ký tk mới xong là phải thêm record vào giỏ hàng
        cartRepository.save(
                Cart.builder()
                        .registeredStudentId(rs)
                        .build()
        );

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Đăng ký thành công")
                .build();
    }

    @Override
    public ResponseObject<Object> checkInformationRegister(String studentId, String identity) {
        RegisteredStudent isRegister = registeredStudentRepository.findRegisteredStudentByStudentId(studentId);
        boolean checkExist = studentRepository.existsById(studentId);
        if (isRegister != null) {
            return ResponseObject.builder()
                    .status(HttpStatus.IM_USED.value())
                    .message(HttpStatus.IM_USED.name())
                    .content("Tài khoản đã được sử dụng!")
                    .build();
        } else if (checkExist) {
            Student studentInfor = studentRepository.getReferenceById(studentId);
            if (
                    studentInfor.getStudentId().equals(studentId)
                            && studentInfor.getIdentityCard().equals(identity)
            ) {
                return ResponseObject.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.name())
                        .content("Thông tin chính xác!")
                        .build();
            }
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Thông tin sinh viên không tồn tại!")
                .build();
    }

    @Override
    public ResponseObject<Object> isRegistered(String studentId) {
        UserDetails registeredStudent = registeredStudentDetailService.loadUserByUsername(studentId);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name().toLowerCase())
                .content("Tài khoản đã được đăng ký")
                .build();
    }

    @Override
    public ResponseObject<Object> staffLogin(StaffLoginRequest staffLoginRequest) {
        UserDetails staff = staffDetailService.loadUserByUsername(staffLoginRequest.getNumberPhone());
        if (!passwordEncoder.matches(staffLoginRequest.getPassword(), staff.getPassword())) {
            return ResponseObject.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(HttpStatus.UNAUTHORIZED.name().toLowerCase())
                    .content("Sai tài khoản hoặc mật khẩu")
                    .build();
        }
        else if (!staff.isAccountNonLocked()) {
            return ResponseObject.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message(HttpStatus.UNAUTHORIZED.name().toLowerCase())
                    .content("Tài khoản bị vô hiệu hóa")
                    .build();
        }
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(staffLoginRequest.getNumberPhone(),
                                staffLoginRequest.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name().toLowerCase())
                .content("Đăng nhập thành công")
                .data(StaffInformationLoginResponse
                        .builder()
                        .staffId(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getStaffId())
                        .username(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getFirstName()+" "+staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getLastName())
                        .gender(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getGender())
                        .identityCard(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getIdentityCard())
                        .phoneNumber(staffLoginRequest.getNumberPhone())
                        .role(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getRoleId().getRoleName())
                        .dob(staffRepository.findStaffByNumberPhone(staffLoginRequest.getNumberPhone()).getDob())
                        .accessToken("123")
                        .build())
                .build();
    }
}
