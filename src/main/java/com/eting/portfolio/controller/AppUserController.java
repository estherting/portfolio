package com.eting.portfolio.controller;

import com.eting.portfolio.dto.AppUserDto;
import com.eting.portfolio.entity.AppUser;
import com.eting.portfolio.mapper.Mapper;
import com.eting.portfolio.service.AppUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    private AppUserService appUserService;
    private Mapper<AppUser, AppUserDto> appUserMapper;

    public AppUserController(AppUserService inService, Mapper<AppUser, AppUserDto> inMapper) {
        appUserService = inService;
        appUserMapper = inMapper;
    }

    @PostMapping(path = "/users")
    public AppUserDto createAppUser(@RequestBody AppUserDto inAppUserDto) {
        AppUser appUser = appUserMapper.mapFrom(inAppUserDto);
        AppUser savedAppUser = appUserService.createAppUser(appUser);
        return appUserMapper.mapTo(savedAppUser);
    }
}
