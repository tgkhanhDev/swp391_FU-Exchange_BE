package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public LoginResponse loginStudent(@RequestBody LoginRequest loginRequest) {
        return authenticationService.loginResponse(loginRequest);
    }
}
