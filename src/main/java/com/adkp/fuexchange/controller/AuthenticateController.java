package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticateController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseObject loginStudent(@RequestBody LoginRequest loginRequest) {
        if (
                loginRequest.getPassword() != null
                        && loginRequest.getUsername() != null
        ) {
            return authenticationService.login(loginRequest);
        }
        return ResponseObject.builder()

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
            return authenticationService.register(registerRequest);
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
        return authenticationService.checkInformationRegister(studentId, identity);
    }

    @GetMapping("/isRegistered/{studentId}")
    public ResponseObject IsRegistered(@PathVariable String studentId) {
        return authenticationService.isRegistered(studentId);
    }
}
