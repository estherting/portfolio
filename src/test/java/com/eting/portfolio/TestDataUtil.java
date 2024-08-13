package com.eting.portfolio;

import com.eting.portfolio.domain.User;

public final class TestDataUtil {

    private TestDataUtil(){}

    public static User createTestUser() {
        return User.builder()
                .name("Michael Scott")
                .email("ms@dundermifflin.com")
                .build();
    }

    public static User createTestUserA() {
        return User.builder()
                .name("Dwight Howard")
                .email("dh@dundermifflin.com")
                .build();
    }
}
