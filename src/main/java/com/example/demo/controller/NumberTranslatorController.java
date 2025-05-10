package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Здесь пока наброски

@Controller
public class NumberTranslatorController {

    @GetMapping("/number_translator")
    public String mainPage() {

        return "main.html";
    }

    @PostMapping("/number_translator")
    public String translateNumber(@RequestParam String numberStr, Model model) {
        System.out.println("Got a number: " + numberStr);
        model.addAttribute("numberStr", numberStr);
        return "main.html";
    }
}
