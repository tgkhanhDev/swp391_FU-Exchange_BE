package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.RegisteredStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Tag(name = "Impact on registered student")
@Validated
public class RegisteredStudentController {

    private final RegisteredStudentService registeredStudentService;

    @Autowired
    public RegisteredStudentController(RegisteredStudentService registeredStudentService) {
        this.registeredStudentService = registeredStudentService;
    }

    @Operation(summary = "View profile of registered student")
    @GetMapping("/{registeredStudentId}")
    public ResponseObject<Object> viewProfile(@PathVariable("registeredStudentId") Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        registeredStudentService.viewProfile(registeredStudentId)
                )
                .build();
    }

    @Operation(summary = "Update of registered student")
    @PutMapping("/update-password")
    public ResponseObject<Object> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {

        return registeredStudentService.updatePassword(updatePasswordRequest);
    }
}
