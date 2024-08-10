package com.eting.portfolio.dao.impl;

import com.eting.portfolio.dao.UserDao;
import com.eting.portfolio.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate inJdbcTemplate) {
        this.jdbcTemplate = inJdbcTemplate;
    }

    public void create(User inUser) {
        jdbcTemplate.update("INSERT INTO users (id, name, email) VALUES (?, ?, ?)",
                                inUser.getId(), inUser.getName(), inUser.getEmail());
    }

    public Optional<User> getById(Long inId) {
        List<User> results = jdbcTemplate.query("SELECT id, name, email FROM users WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                inId);

        return results.stream().findFirst();
    }

    public static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .build();
        }
    }

}
