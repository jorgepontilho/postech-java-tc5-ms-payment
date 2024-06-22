package com.postech.mspayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("product/{id}/{quantity}/exists")
    public String helloWorld2() {
        return "fake url";
    }

    @GetMapping("/customer/{id}/exists")
    public String helloWorld3() {
        return "fake url customer";
    }

}
