package com.example.demo.repository;

import com.example.demo.model.BaseType;
import com.example.demo.model.DeclinationsModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;

public class DeclinationsRepository {
    private final JdbcTemplate jdbc;

    public DeclinationsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public DeclinationsModel getRepresentationForCategory(BaseType category, int partLength) throws SQLException{
        int numberToPass = partLength;
        if(category == BaseType.INTEGER) {
            switch (partLength) {
                case 4, 5, 6:
                    numberToPass = 4;
                    break;
                case 7,8,9:
                    numberToPass = 7;
                    break;
                case 10,11,12:
                    numberToPass = 10;
                    break;
                case 13,14,15:
                    numberToPass = 13;
                    break;
                default:
                    throw new SQLException("Can't find declination for a number!");
            }
        }

        String query =
                """
                SELECT category, number, singular, few, many, fraction_base_id
                FROM declinations d
                LEFT JOIN fraction_bases fb
                ON d.fraction_base_id = fb.id
                WHERE d.category = ? AND d.number = ?;
                """;
        RowMapper<DeclinationsModel> declinationsRowMapper = (r,i) ->
                new DeclinationsModel(
                        BaseType.valueOf(
                        r.getString("category")),
                        r.getInt("number"),
                        r.getString("singular"),
                        r.getString("few"),
                        r.getString("many"),
                        (r.getObject("fraction_base_id") != null) ? r.getInt("fraction_base_id") : 0,
                        r.getString("base")
                        );
        return jdbc.queryForObject(query, declinationsRowMapper, category, numberToPass);
    }
}
