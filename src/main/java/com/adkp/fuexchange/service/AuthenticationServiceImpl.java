package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.RegisteredStudent;
import com.adkp.fuexchange.pojo.Student;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.RoleRepository;
import com.adkp.fuexchange.repository.StudentRepository;
import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.InforLoginResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.security.RegisteredStudentDetailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RegisteredStudentDetailService registeredStudentDetailService;
    private final RegisteredStudentRepository registeredStudentRepository;
    private final AuthenticationManager authenticationManager;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, RegisteredStudentDetailService registeredStudentDetailService, RegisteredStudentRepository registeredStudentRepository, StudentRepository studentRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.registeredStudentDetailService = registeredStudentDetailService;
        this.authenticationManager = authenticationManager;
        this.registeredStudentRepository = registeredStudentRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseObject login(LoginRequest loginRequest) {
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
        return new ResponseObject(
                HttpStatus.OK.value(),
                HttpStatus.OK.name().toLowerCase(),
                "Đăng nhập thành công",
                InforLoginResponse
                        .builder()
                        .username(registeredStudent.getUsername())
                        .role(registeredStudent.getAuthorities().toString())
                        .accessToken("123")
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseObject register(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name().toLowerCase())
                    .content("Mật khẩu xác nhận không trùng khớp!")
                    .build();
        }
        registeredStudentRepository.save(
                RegisteredStudent.builder()
                        .studentId(studentRepository.getReferenceById(registerRequest.getStudentId()))
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .roleId(roleRepository.getReferenceById(1))
                        .isActive(true)
                        .build()
        );
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Đăng ký thành công")
                .build();
    }

    @Override
    public ResponseObject checkInformationRegister(String studentId, String identity) {

        boolean checkExist = studentRepository.existsById(studentId);

        if (checkExist) {
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
    public ResponseObject isRegistered(String studentId) {
        UserDetails registeredStudent = registeredStudentDetailService.loadUserByUsername(studentId);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name().toLowerCase())
                .content("OK")
                .build();
    }
}
