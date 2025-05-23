package com.example.demo.controller;

import com.example.demo.service.InvalidCharException;
import com.example.demo.service.NumberTranslatorService;
import com.example.demo.service.StartsFromZeroException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring6.context.SpringContextUtils;

import java.sql.SQLException;

//Здесь пока наброски

@Controller
public class NumberTranslatorController {

    private final NumberTranslatorService translatorService;

    @Autowired
    public NumberTranslatorController(NumberTranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @GetMapping("/number_translator")
    public String mainPage() {

        return "main.html";
    }

    @PostMapping("/number_translator")
    public String translateNumber(@RequestParam String numberStr, Model model) {
        System.out.println("Got a number: " + numberStr);

        try {
            String result = translatorService.printNumber(numberStr);
            System.out.println("After processing: " + result);
            model.addAttribute("translatedResult", result);
        } catch (StartsFromZeroException e) {
            System.out.println("Error during translation: " + e.getMessage());
            model.addAttribute("text", "Ошибка при переводе: " + e.getMessage());
        } catch (InvalidCharException | SQLException e) {
        System.out.println("Error during translation: " + e.getMessage());
        model.addAttribute("text", "Ошибка при переводе: " + e.getMessage());
    }
        return "main.html";
    }
}
