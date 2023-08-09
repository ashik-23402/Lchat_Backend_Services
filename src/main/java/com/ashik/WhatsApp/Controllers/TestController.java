package com.ashik.WhatsApp.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class TestController {

    @RequestMapping("/hello")
    public ResponseEntity<String> hello(){
        return  ResponseEntity.ok("hello from test api");
    }
}
