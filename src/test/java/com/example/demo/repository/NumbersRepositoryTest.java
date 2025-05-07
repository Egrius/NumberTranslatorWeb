package com.example.demo.repository;

import com.example.demo.model.DigitsModel;
import com.example.demo.model.NumbersModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NumbersRepositoryTest {
    private final JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
    private final NumbersRepository numbersRepository = new NumbersRepository(jdbc);

    @Test
    void testGetRepresentationByValue() {
        when(jdbc.queryForObject(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(new NumbersModel(14, "четырнадцать"));

        NumbersModel result = numbersRepository.getRepresentationByValue(14);
        assertEquals("четырнадцать", result.getNumberStr().trim());
    }
}