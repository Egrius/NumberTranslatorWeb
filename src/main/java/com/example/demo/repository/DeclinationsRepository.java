package com.example.demo.repository;

import com.example.demo.model.BaseType;
import com.example.demo.model.DeclinationsModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class DeclinationsRepository {
    private final JdbcTemplate jdbc;

    public DeclinationsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public String getIntDeclination(int number, int partLength) throws SQLException{
        if (partLength < 4) {
            return "";
        }
        DeclinationsModel resultSet = getRepresentationForCategory(BaseType.INTEGER, partLength);
        if (number == 1) return resultSet.getSingular();
        else if (number >= 2 && number <= 4) return resultSet.getFew();
        else return resultSet.getMany();
    }

    public String getFractionalDeclination (int number, int count) throws SQLException{
        DeclinationsModel resultSet = getRepresentationForCategory(BaseType.FRACTIONAL, count);
        String base = resultSet.getFraction_base();
        String ending =  (number == 1) ? resultSet.getSingular() : resultSet.getMany();
        return base + ending;
    }

    protected DeclinationsModel getRepresentationForCategory(BaseType category, int partLength) throws SQLException{
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
                SELECT
                d.category,
                d.number,
                d.singular,
                d.few,
                d.many,
                d.fraction_base_id,
                COALESCE(fb.base, '') AS base
                FROM declinations d
                LEFT JOIN fraction_bases fb ON d.fraction_base_id = fb.id
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
        return jdbc.queryForObject(query, declinationsRowMapper, category.name(), numberToPass);
    }
}