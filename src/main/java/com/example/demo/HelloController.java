package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "tail_test";
    }
    @GetMapping("/show")
    public String show() {
        return "/fragment/nav.html";
    }
}
