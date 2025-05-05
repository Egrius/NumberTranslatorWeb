package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

//Здесь пока наброски

@RestController
@RequestMapping("/number_translator")
public class NumberTranslatorController {

    @GetMapping("/translate")
    public String translateNumber(@RequestParam String number) {
        return "";
    }
}
