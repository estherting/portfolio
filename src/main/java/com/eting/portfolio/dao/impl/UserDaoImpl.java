package com.eting.portfolio.dao.impl;

import com.eting.portfolio.dao.UserDao;
import com.eting.portfolio.entity.AppUser;
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

    public void create(AppUser inAppUser) {
        SimpleJdbcInsert insertIntoUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("app_user").usingGeneratedKeyColumns("id");
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", inAppUser.getName());
        parameters.put("email", inAppUser.getEmail());

        Number id = insertIntoUser.executeAndReturnKey(parameters);
        inAppUser.setId(id.longValue());

//        jdbcTemplate.update("INSERT INTO users (name, email) VALUES (?, ?)",
//                                inUser.getName(), inUser.getEmail());
    }

    public Optional<AppUser> getById(Long inId) {
        List<AppUser> results = jdbcTemplate.query("SELECT id, name, email FROM " + UserDaoImpl.TABLE_NAME + " WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                inId);

        return results.stream().findFirst();
    }

    public List<AppUser> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + UserDaoImpl.TABLE_NAME,
                new UserRowMapper());
    }

    public void update(AppUser inAppUser) {
        jdbcTemplate.update("UPDATE " + UserDaoImpl.TABLE_NAME + " SET id = ?, name = ?, email = ?",
                inAppUser.getId(), inAppUser.getName(), inAppUser.getEmail());
    }

    public void delete(Long inId) {
        jdbcTemplate.update("DELETE FROM " + UserDaoImpl.TABLE_NAME + " WHERE id = ?",
                inId);
    }

    public static class UserRowMapper implements RowMapper<AppUser> {
        @Override
        public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            return AppUser.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .build();
        }
    }

}
