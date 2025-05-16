package com.example.demo.repository;

import com.example.demo.model.NumbersModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NumbersRepository extends BaseRepository<NumbersModel, Integer> {
    public NumbersRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    public NumbersModel getRepresentationByValue(Integer value) {
        String query =
                """
                SELECT number, number_str FROM numbers WHERE number = ?;
                """;
        RowMapper<NumbersModel> numbersRowMapper = (r, i) ->
                new NumbersModel(r.getInt("number"), r.getString("number_str"));

        return jdbc.queryForObject(query, numbersRowMapper, value);
    }
}
