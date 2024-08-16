package com.eting.portfolio.controller;

import com.eting.portfolio.dto.AppUserDto;
import com.eting.portfolio.entity.AppUser;
import com.eting.portfolio.mapper.Mapper;
import com.eting.portfolio.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AppUserController {

    private AppUserService appUserService;
    private Mapper<AppUser, AppUserDto> appUserMapper;

    public AppUserController(AppUserService inService, Mapper<AppUser, AppUserDto> inMapper) {
        appUserService = inService;
        appUserMapper = inMapper;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<AppUserDto> createAppUser(@RequestBody AppUserDto inAppUserDto) {
        AppUser appUser = appUserMapper.mapFrom(inAppUserDto);
        AppUser savedAppUser = appUserService.save(appUser);
        return new ResponseEntity<>(appUserMapper.mapTo(savedAppUser), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public List<AppUserDto> listAppUsers() {
        List<AppUser> appUsers = appUserService.findAll();
        return appUsers.stream()
                .map(appUserMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<AppUserDto> getAppUserById(@PathVariable("id") Long inId) {
        Optional<AppUser> foundAppUser = appUserService.getAppUser(inId);
        return foundAppUser.map(appUser ->
        {AppUserDto appUserDto = appUserMapper.mapTo(appUser);
            return new ResponseEntity<>(appUserDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<AppUserDto> fullUpdateAppUser(@PathVariable("id") Long inId,
                                                        @RequestBody AppUserDto inAppUserDto) {
        // check if user exists in db
        if (! appUserService.isExists(inId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        inAppUserDto.setId(inId);
        AppUser appUser = appUserMapper.mapFrom(inAppUserDto);
        AppUser savedAppUser = appUserService.save(appUser);
        return new ResponseEntity<>(appUserMapper.mapTo(savedAppUser), HttpStatus.OK);
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<AppUserDto> partialUpdateAppUser(@PathVariable("id") Long inId,
                                                           @RequestBody AppUserDto inAppUserDto) {
        // check if user exists in db
        if (! appUserService.isExists(inId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AppUser appUser = appUserMapper.mapFrom(inAppUserDto);
        AppUser updatedAppUser = appUserService.partialUpdate(inId, appUser);
        return new ResponseEntity<>(appUserMapper.mapTo(updatedAppUser), HttpStatus.OK);
    }

    @DeleteMapping(path = "users/{id}")
    public ResponseEntity<AppUserDto> deleteAppUser(@PathVariable("id") Long inId) {
        appUserService.delete(inId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
