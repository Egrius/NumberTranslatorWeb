package com.example.demo.repository;

import com.example.demo.model.DigitsModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class DigitsRepository extends BaseRepository<DigitsModel, Integer>{

    public DigitsRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    public DigitsModel getRepresentationByValue(Integer value) {
        String query =
                """
                SELECT number, number_str FROM digits WHERE number = ?;
                """;
        RowMapper<DigitsModel> digitsRowMapper = (r,i) ->
                new DigitsModel(r.getInt("number"), r.getString("number_str"));

        return jdbc.queryForObject(query, digitsRowMapper, value);
    }
}