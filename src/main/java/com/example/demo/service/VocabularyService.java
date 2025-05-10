package com.example.demo.service;

import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class VocabularyService {
    private final DeclinationsRepository declinationsRepository;
    private final HundredsRepository hundredsRepository;
    private final TensRepository tensRepository;
    private final NumbersRepository numbersRepository;
    private final DigitsRepository digitsRepository;

    public VocabularyService(DeclinationsRepository declinationsRepository,
                             HundredsRepository hundredsRepository,
                             TensRepository tensRepository,
                             NumbersRepository numbersRepository,
                             DigitsRepository digitsRepository) {
        this.declinationsRepository = declinationsRepository;
        this.hundredsRepository = hundredsRepository;
        this.tensRepository = tensRepository;
        this.numbersRepository = numbersRepository;
        this.digitsRepository = digitsRepository;
    }
}
