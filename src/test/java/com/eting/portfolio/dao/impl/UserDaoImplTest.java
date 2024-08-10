package com.eting.portfolio.dao.impl;

import com.eting.portfolio.dao.impl.UserDaoImpl;
import com.eting.portfolio.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl underTest;

    @Test
    public void testGenerateCreateSql() {
        User user = User.builder()
                .id(1L)
                .name("Harry Potter")
                .email("hp@gmail.com")
                .build();

        underTest.create(user);

        verify(jdbcTemplate).update(
                eq("INSERT INTO users (id, name, email) VALUES (?, ?, ?)"),
                eq(1L), eq("Harry Potter"), eq("hp@gmail.com")
        );
    }

}
