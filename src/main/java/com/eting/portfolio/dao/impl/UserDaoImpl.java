package com.eting.portfolio.dao.impl;

import com.eting.portfolio.dao.UserDao;
import com.eting.portfolio.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate inJdbcTemplate) {
        this.jdbcTemplate = inJdbcTemplate;
    }

    public void create(User inUser) {
        jdbcTemplate.update("INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
                                inUser.getId(), inUser.getName(), inUser.getEmail());
    }
}
