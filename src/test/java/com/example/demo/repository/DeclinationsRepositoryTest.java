package com.example.demo.repository;

import com.example.demo.model.BaseType;
import com.example.demo.model.DeclinationsModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class DeclinationsRepositoryTest {
    private final JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
    private final DeclinationsRepository declinationsRepository = new DeclinationsRepository(jdbc);

    @Test
    void testGetRepresentationByParams() throws SQLException {
        DeclinationsModel expected = new DeclinationsModel(
                BaseType.INTEGER, 10, "ая", null, "ых", 10, "десятитриллионн");

        when(jdbc.queryForObject(anyString(), any(RowMapper.class), eq(BaseType.INTEGER.name()), eq(10)))
                .thenReturn(expected);

        DeclinationsModel result = declinationsRepository.getRepresentationForCategory(BaseType.INTEGER, 10);

        assertNotNull(result, "Результат не должен быть null");
        assertEquals(expected.getBase(), result.getBase());
        assertEquals(expected.getNumber(), result.getNumber());
        assertEquals(expected.getSingular(), result.getSingular());
        assertEquals(expected.getFew(), result.getFew());
        assertEquals(expected.getMany(), result.getMany());
        assertEquals(expected.getFraction_base_id(), result.getFraction_base_id());
        assertEquals(expected.getFraction_base(), result.getFraction_base());
    }
}