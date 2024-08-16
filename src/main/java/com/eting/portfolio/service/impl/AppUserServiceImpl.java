package com.eting.portfolio.service.impl;

import com.eting.portfolio.entity.AppUser;
import com.eting.portfolio.repository.AppUserRepository;
import com.eting.portfolio.service.AppUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser save(AppUser inAppUser) {
        return appUserRepository.save(inAppUser);
    }

    @Override
    public List<AppUser> findAll() {
        return StreamSupport.stream(appUserRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppUser> getAppUser(Long inId) {
        return appUserRepository.findById(inId);
    }

    @Override
    public AppUser partialUpdate(Long inId, AppUser inAppUser) {
        inAppUser.setId(inId);

        // get user from db, then update
        return appUserRepository.findById(inId).map(existingAppUser -> {
            Optional.ofNullable(inAppUser.getName()).ifPresent(existingAppUser::setName);
            Optional.ofNullable(inAppUser.getEmail()).ifPresent(existingAppUser::setEmail);
            return appUserRepository.save(existingAppUser);
        }).orElseThrow(() -> new RuntimeException("AppUser does not exist"));
    }

    @Override
    public void delete(Long inId) {
        appUserRepository.deleteById(inId);
    }

    @Override
    public boolean isExists(Long inId) {
        return appUserRepository.existsById(inId);
    }
}
