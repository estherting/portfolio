package com.eting.portfolio.dao.impl;

import com.eting.portfolio.PortfolioApplication;
import com.eting.portfolio.TestDataUtil;
import com.eting.portfolio.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = PortfolioApplication.class)
@ExtendWith(SpringExtension.class)
public class UserDaoImplIntegrationTest {

    private UserDaoImpl underTest;

    @Autowired
    public UserDaoImplIntegrationTest(UserDaoImpl inUserDaoImpl) {
        underTest = inUserDaoImpl;
    }

    @Test
    public void testCRUD() {
        User user = TestDataUtil.createTestUser();
        Optional<User> result;

        try {
            // CREATE
            underTest.create(user);

            // READ
            result = underTest.getById(user.getId());
            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(user);

            // UPDATE
            User userUpdate = TestDataUtil.createTestUserA();
            user.setName(userUpdate.getName());
            user.setEmail(userUpdate.getEmail());
            underTest.update(user);
            result = underTest.getById(user.getId());
            assertThat(result.get().getName()).isEqualTo(userUpdate.getName());
        }
        finally {
            // DELETE
            underTest.delete(user.getId());
            result = underTest.getById(user.getId());
            assertThat(result).isNotPresent();
        }
    }

    @Test
    public void testGetAll() {
        User user = TestDataUtil.createTestUser();

        try {
            underTest.create(user);

            List<User> results = underTest.getAll();
            assertThat(results).isNotEmpty();
        }
        finally {
            if (user.getId() != null) {
                underTest.delete(user.getId());
            }
        }


    }

}
