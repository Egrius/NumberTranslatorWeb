package com.example.demo.repository;

import com.example.demo.model.HundredsModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HundredsRepository extends BaseRepository<HundredsModel, Integer>{
    public HundredsRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @Override
    public HundredsModel getRepresentationByValue(Integer value) {
        String query =
                """
                SELECT number, number_str FROM hundreds WHERE number = ?;
                """;
        RowMapper<HundredsModel> hundredsRowMapper = (r, i) ->
                new HundredsModel(r.getInt("number"), r.getString("number_str"));

        return jdbc.queryForObject(query, hundredsRowMapper, value);
    }
}