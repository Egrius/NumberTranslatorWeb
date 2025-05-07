package com.example.demo.repository;

import com.example.demo.model.DigitsModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DigitsRepositoryTest {

    private final JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
    private final DigitsRepository digitsRepository = new DigitsRepository(jdbc);

    @Test
    void testGetRepresentationByValue() {
        when(jdbc.queryForObject(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(new DigitsModel(5, "пять"));
        DigitsModel result = digitsRepository.getRepresentationByValue(5);
        assertEquals("пять", result.getNumberStr().trim());
    }
}