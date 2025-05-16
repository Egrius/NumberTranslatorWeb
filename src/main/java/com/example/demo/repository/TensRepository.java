package com.example.demo.repository;

import com.example.demo.model.NumbersModel;
import com.example.demo.model.TensModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class TensRepository extends BaseRepository<TensModel,Integer>{
    public TensRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    public TensModel getRepresentationByValue(Integer value) {
        String query =
                """
                SELECT number, number_str FROM tens WHERE number = ?;
                """;
        RowMapper<TensModel> tensRowMapper = (r, i) ->
                new TensModel(r.getInt("number"), r.getString("number_str"));

        return jdbc.queryForObject(query, tensRowMapper, value);
    }
}