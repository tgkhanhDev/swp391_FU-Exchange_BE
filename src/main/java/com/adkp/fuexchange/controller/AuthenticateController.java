package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.CheckInformationRequest;
import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name="Authentication")
public class AuthenticateController {

    private final AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    public AuthenticateController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/login")
    public LoginResponse loginStudent(@RequestBody LoginRequest loginRequest) {
        return authenticationServiceImpl.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseObject registerStudent(@RequestBody RegisterRequest registerRequest) {
        return authenticationServiceImpl.register(registerRequest);
    }

    @PostMapping("/check-information")
    public ResponseObject checkInformationRegister(@RequestBody CheckInformationRequest checkInformationRequest){
        return authenticationServiceImpl.checkInformationRegister(checkInformationRequest);
    }

    @GetMapping("/isRegistered/{studentId}")
    public LoginResponse IsRegistered(@PathVariable String studentId){
        return authenticationServiceImpl.isRegistered(studentId);
    }
}
