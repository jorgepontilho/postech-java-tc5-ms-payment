package com.postech.mspayment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
/**
 *
 @GetMapping({"product/{id}/{quantity}/exists"})
 public java.lang.String helloWorld2() {

 return "Hello propduct";
 }

 @GetMapping({"/customer/{id}/exists"})
 public java.lang.String helloWorld3() {

 return "Hello customer";
 }
 */

}
