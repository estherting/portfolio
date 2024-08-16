package com.eting.portfolio.service.impl;

import com.eting.portfolio.entity.AppUser;
import com.eting.portfolio.repository.AppUserRepository;
import com.eting.portfolio.service.AppUserService;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser createAppUser(AppUser inAppUser) {
        return appUserRepository.save(inAppUser);
    }
}
