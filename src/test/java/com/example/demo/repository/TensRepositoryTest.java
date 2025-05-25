package com.example.demo.repository;

import com.example.demo.model.TensModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class TensRepositoryTest {
    private final JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
    private final TensRepository tensRepository = new TensRepository(jdbc);

    @Test
    void testGetRepresentationByValue() {
        when(jdbc.queryForObject(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(new TensModel(8, "восемьдесят"));

        TensModel result = tensRepository.getRepresentationByValue(8);
        assertEquals("восемьдесят", result.getNumberStr().trim());
    }
}