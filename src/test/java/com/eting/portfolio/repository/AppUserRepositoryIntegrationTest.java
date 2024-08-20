package com.eting.portfolio.repository;

import com.eting.portfolio.PortfolioApplication;
import com.eting.portfolio.TestDataUtil;
import com.eting.portfolio.entity.AppUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = PortfolioApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
public class AppUserRepositoryIntegrationTest {

    private AppUserRepository underTest;

    @Autowired
    public AppUserRepositoryIntegrationTest(AppUserRepository inAppUserRepo) {
        underTest = inAppUserRepo;
    }

    @Test
    public void testCRUD() {
        AppUser appUser = TestDataUtil.createTestUser();
        Optional<AppUser> result;

        try {
            // CREATE
            underTest.save(appUser);

            // READ
            result = underTest.findById(appUser.getId());
            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(appUser);

            // UPDATE
            AppUser appUserUpdate = TestDataUtil.createTestUserA();
            appUser.setName(appUserUpdate.getName());
            appUser.setEmail(appUserUpdate.getEmail());
            underTest.save(appUser);
            result = underTest.findById(appUser.getId());
            assertThat(result.get().getName()).isEqualTo(appUserUpdate.getName());
        }
        finally {
            // DELETE
            underTest.delete(appUser);
            result = underTest.findById(appUser.getId());
            assertThat(result).isNotPresent();
        }
    }

    @Test
    public void testGetAll() {
        AppUser appUser = TestDataUtil.createTestUser();

        try {
            underTest.save(appUser);

            Iterable<AppUser> results = underTest.findAll();
            assertThat(results).isNotEmpty();
        }
        finally {
            if (appUser.getId() != null) {
                underTest.delete(appUser);
            }
        }


    }

}
