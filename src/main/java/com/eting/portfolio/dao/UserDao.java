package com.eting.portfolio.dao;

import com.eting.portfolio.domain.User;

import java.util.Optional;
import java.util.List;

public interface UserDao {
    void create(User user);
    Optional<User> getById(Long inId);
    List<User> getAll();
    void update(User inUser);
    void delete(Long inId);
}
