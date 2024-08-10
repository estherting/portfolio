package com.eting.portfolio.dao;

import com.eting.portfolio.domain.User;

import java.util.Optional;

public interface UserDao {
    void create(User user);
    Optional<User> getById(Long inId);
}
