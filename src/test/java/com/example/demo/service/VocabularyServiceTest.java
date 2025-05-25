package com.example.demo.service;

import com.example.demo.model.BaseType;
import com.example.demo.model.DigitsModel;
import com.example.demo.model.HundredsModel;
import com.example.demo.model.TensModel;
import com.example.demo.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class VocabularyServiceTest {

    @Mock
    private DeclinationsRepository declinationsRepository;

    @Mock
    private HundredsRepository hundredsRepository;

    @Mock
    private TensRepository tensRepository;

    @Mock
    private NumbersRepository numbersRepository;

    @Mock
    private DigitsRepository digitsRepository;

    private VocabularyService vocabularyService;

    @BeforeEach
    void setUp() {
        vocabularyService = new VocabularyService(
                                                    declinationsRepository,
                                                    hundredsRepository,
                                                    tensRepository,
                                                    numbersRepository,
                                                    digitsRepository
                                                  );
    }

    @Test
    void getRightTranslation() {

        when(hundredsRepository.getRepresentationByValue(1))
                .thenReturn(new HundredsModel(1, "сто"));
        when(tensRepository.getRepresentationByValue(2))
                .thenReturn(new TensModel(2, "двадцать"));
        when(digitsRepository.getRepresentationByValue(3))
                .thenReturn(new DigitsModel(3, "три"));
        try {
            when(declinationsRepository.getIntDeclination(123, 1))
                    .thenReturn("сто двадцать три");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String result = vocabularyService.translateNumber(123, 1, BaseType.INTEGER );
            assertEquals("сто двадцать три", result);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}