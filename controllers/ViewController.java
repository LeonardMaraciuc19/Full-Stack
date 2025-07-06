package com.its.library.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ViewController {
     @GetMapping("/")
    public String home() {
        return "index"; // carica resources/templates/index.html
    }
}
