package com.adkp.fuexchange.controller;


import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.web.bind.annotation.*;
@RestController
public class HomeController {

    @GetMapping("/login")
    public ResponseObject loginSuccess(){
        return new ResponseObject(200, "Success");
    }
}
