package com.eting.portfolio.dao;

import com.eting.portfolio.entity.AppUser;

import java.util.Optional;
import java.util.List;

public interface UserDao {
    void create(AppUser appUser);
    Optional<AppUser> getById(Long inId);
    List<AppUser> getAll();
    void update(AppUser inAppUser);
    void delete(Long inId);
}
