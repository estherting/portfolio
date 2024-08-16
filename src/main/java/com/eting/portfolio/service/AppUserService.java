package com.eting.portfolio.service;

import com.eting.portfolio.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    public AppUser save(AppUser inAppUser);
    public List<AppUser> findAll();
    public Optional<AppUser> getAppUser(Long inId);
    public AppUser partialUpdate(Long inId, AppUser appUser);
    public void delete(Long inId);
    public boolean isExists(Long inId);

}
