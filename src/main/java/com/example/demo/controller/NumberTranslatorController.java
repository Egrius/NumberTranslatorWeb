package com.example.demo.controller;

import com.example.demo.service.NumberTranslatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Здесь пока наброски

@Controller
public class NumberTranslatorController {

    private final NumberTranslatorService numberTranslatorService;

    public NumberTranslatorController(NumberTranslatorService numberTranslatorService) {
        this.numberTranslatorService = numberTranslatorService;
    }

    @GetMapping("/number_translator")
    public String mainPage() {

        return "main.html";
    }

    @PostMapping("/number_translator")
    public String translateNumber(@RequestParam String numberStr, Model model) {
        System.out.println("Got a number: " + numberStr);
        model.addAttribute("numberStr", numberStr);
        numberTranslatorService.test(numberStr);
        return "main.html";
    }
}
