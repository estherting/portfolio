package com.eting.portfolio.dao.impl;

import com.eting.portfolio.dao.UserDao;
import com.eting.portfolio.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    public static final String TABLE_NAME = "app_user";
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate inJdbcTemplate) {
        this.jdbcTemplate = inJdbcTemplate;
    }

    public void create(User inUser) {
        SimpleJdbcInsert insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("app_user").usingGeneratedKeyColumns("id");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", inUser.getName());
        parameters.put("email", inUser.getEmail());

        Number id = insertIntoUser.executeAndReturnKey(parameters);
        inUser.setId(id.longValue());

//        jdbcTemplate.update("INSERT INTO users (name, email) VALUES (?, ?)",
//                                inUser.getName(), inUser.getEmail());
    }

    public Optional<User> getById(Long inId) {
        List<User> results = jdbcTemplate.query("SELECT id, name, email FROM " + UserDaoImpl.TABLE_NAME + " WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                inId);

        return results.stream().findFirst();
    }

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + UserDaoImpl.TABLE_NAME,
                new UserRowMapper());
    }

    public void update(User inUser) {
        jdbcTemplate.update("UPDATE " + UserDaoImpl.TABLE_NAME + " SET id = ?, name = ?, email = ?",
                inUser.getId(), inUser.getName(), inUser.getEmail());
    }

    public void delete(Long inId) {
        jdbcTemplate.update("DELETE FROM " + UserDaoImpl.TABLE_NAME + " WHERE id = ?",
                inId);
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
