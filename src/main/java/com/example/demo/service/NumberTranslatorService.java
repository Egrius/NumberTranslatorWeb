package com.example.demo.service;

import com.example.demo.service.VocabularyService;
import org.springframework.stereotype.Service;

@Service
public class NumberTranslatorService {
    private final VocabularyService vocabulary;

    public NumberTranslatorService(VocabularyService vocabulary) {
        this.vocabulary = vocabulary;
    }
}
