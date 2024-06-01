package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticateController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    public AuthenticateController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/login")
    public LoginResponse loginStudent(@RequestBody LoginRequest loginRequest) {
        if (
                loginRequest.getPassword() != null
                        && loginRequest.getUsername() != null
        ) {
            return authenticationServiceImpl.login(loginRequest);
        }
        return LoginResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin")
                .build();
    }

    @PostMapping("/register")
    public ResponseObject registerStudent(@RequestBody RegisterRequest registerRequest) {
        if (
                registerRequest.getPassword() != null
                        && registerRequest.getConfirmPassword() != null
                        && registerRequest.getStudentId() != null
                        && registerRequest.getIdentifyNumber() != null
        ) {
            return authenticationServiceImpl.register(registerRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin")
                .build();
    }

    @GetMapping("/check-information")
    public ResponseObject checkInformationRegister(
            @RequestParam("studentId") String studentId,
            @RequestParam("identity") String identity
    ) {
        return authenticationServiceImpl.checkInformationRegister(studentId, identity);
    }

    @GetMapping("/isRegistered/{studentId}")
    public LoginResponse IsRegistered(@PathVariable String studentId) {
        return authenticationServiceImpl.isRegistered(studentId);
    }
}
