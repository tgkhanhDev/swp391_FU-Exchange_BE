package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.response.InforLoginResponse;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.security.RegisteredStudentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final RegisteredStudentDetailService registeredStudentDetailService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, RegisteredStudentDetailService registeredStudentDetailService, BCryptPasswordEncoder passwordEncoder) {
        this.registeredStudentDetailService = registeredStudentDetailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse loginResponse(LoginRequest loginRequest) {

        UserDetails registeredStudent = registeredStudentDetailService.loadUserByUsername(loginRequest.getUsername());

        if (registeredStudent == null || !passwordEncoder.matches(loginRequest.getPassword(), registeredStudent.getPassword())) {
            return LoginResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message(HttpStatus.UNAUTHORIZED.name().toLowerCase())
                    .content("Sai tài khoản hoặc mật khẩu")
                    .build();
        } else if (!registeredStudent.isAccountNonLocked()) {
            return new LoginResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    HttpStatus.UNAUTHORIZED.name().toLowerCase(),
                    "Tài khoản bị vô hiệu hóa",
                    InforLoginResponse.builder()
                            .username(registeredStudent.getUsername())
                            .role(registeredStudent.getAuthorities().toString())
                            .accessToken("123")
                            .build());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponse(HttpStatus.OK.value(),
                HttpStatus.OK.name().toLowerCase(),
                "Đăng nhập thành công",
                InforLoginResponse.builder()
                        .username(registeredStudent.getUsername())
                        .role(registeredStudent.getAuthorities().toString())
                        .accessToken("123")
                        .build());
    }

    public LoginResponse isRegistered(String studentId) {
        try {
            UserDetails registeredStudent = registeredStudentDetailService.loadUserByUsername(studentId);
            if (registeredStudent == null) {
                return LoginResponse.builder()
                        .statusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value())
                        .message(HttpStatus.NON_AUTHORITATIVE_INFORMATION.name().toLowerCase())
                        .content("Tài khoản chưa tồn tại")
                        .build();
            } else {
                return LoginResponse.builder()
                        .statusCode(HttpStatus.ACCEPTED.value())
                        .message(HttpStatus.ACCEPTED.name().toLowerCase())
                        .content("OK")
                        .build();
            }
        } catch (UsernameNotFoundException usernameNotFoundException) {
            return LoginResponse.builder()
                    .statusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION.value())
                    .message(HttpStatus.NON_AUTHORITATIVE_INFORMATION.name().toLowerCase())
                    .content("Tài khoản chưa tồn tại")
                    .build();
        }

    }
}
