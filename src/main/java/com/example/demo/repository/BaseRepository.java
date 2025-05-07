package com.example.demo.repository;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseRepository<T, V>{
    protected final JdbcTemplate jdbc;

    public BaseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    public abstract T getRepresentationByValue(V v);
}
