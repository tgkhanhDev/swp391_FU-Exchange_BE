package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.RegisteredStudentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@Tag(name = "Impact on registered student")
public class RegisteredStudentController {

    private final RegisteredStudentService registeredStudentService;

    @Autowired
    public RegisteredStudentController(RegisteredStudentService registeredStudentService) {
        this.registeredStudentService = registeredStudentService;
    }

    @GetMapping("/{registeredStudentId}")
    public RegisteredStudentDTO viewProfile(@PathVariable("registeredStudentId") Integer registeredStudentId){
        return registeredStudentService.viewProfile(registeredStudentId);
    }

    @PutMapping("/update-password")
    public ResponseObject updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        return registeredStudentService.updatePassword(updatePasswordRequest);
    }
}
