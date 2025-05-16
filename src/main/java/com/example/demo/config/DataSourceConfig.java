package com.example.demo.config;

import com.example.demo.repository.*;
import com.example.demo.service.NumberTranslatorService;
import com.example.demo.service.VocabularyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public VocabularyService vocabularyService(DeclinationsRepository declinationsRepository,
                                               HundredsRepository hundredsRepository,
                                               TensRepository tensRepository,
                                               NumbersRepository numbersRepository,
                                               DigitsRepository digitsRepository) {
        return new VocabularyService(declinationsRepository, hundredsRepository, tensRepository, numbersRepository, digitsRepository);
    }
    @Bean
    public NumberTranslatorService numberTranslatorService(VocabularyService vocabularyService) {
        return new NumberTranslatorService(vocabularyService);
    }
}
