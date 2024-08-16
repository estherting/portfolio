package com.eting.portfolio.controller;

import com.eting.portfolio.PortfolioApplication;
import com.eting.portfolio.TestDataUtil;
import com.eting.portfolio.entity.AppUser;
import com.eting.portfolio.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PortfolioApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {

    private MockMvc mockMvc;
    private AppUserService appUserService;
    private ObjectMapper objectMapper;

    @Autowired
    public AppUserControllerIntegrationTest(MockMvc mockMvc, AppUserService appUserService) {
        this.mockMvc = mockMvc;
        this.appUserService = appUserService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateAppUserStatusCreated() throws Exception {
        AppUser appUser = TestDataUtil.createTestUser();
        String appUserJson = objectMapper.writeValueAsString(appUser);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appUserJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testFindAllStatusOk() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testFindAll() throws Exception {
        // first, populate the database
        AppUser user = TestDataUtil.createTestUser();
        appUserService.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(user.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value(user.getEmail())
        );
    }

    @Test
    public void testGetById() throws Exception {
        // first, populate the database
        AppUser user = TestDataUtil.createTestUser();
        appUserService.save(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("name").value(user.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("email").value(user.getEmail())
        );
    }

    @Test
    public void testFullUpdateAppUser() throws Exception {
        // create user
        AppUser appUser = TestDataUtil.createTestUser();
        AppUser savedAppUser = appUserService.save(appUser);

        // set new values on user
        AppUser appUser1 = TestDataUtil.createTestUserA();
        appUser1.setId(savedAppUser.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedAppUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("name").value(appUser1.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("email").value(appUser1.getEmail())
        );
    }

    @Test
    public void testPartialUpdateAppUser() throws Exception {
        // create user
        AppUser appUser = TestDataUtil.createTestUser();
        AppUser savedAppUser = appUserService.save(appUser);

        // set new email on user
        AppUser appUser1 = TestDataUtil.createTestUserA();
        appUser1.setId(savedAppUser.getId());
        appUser1.setName(null);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + appUser1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appUser1))
        ).andExpect(
                MockMvcResultMatchers.jsonPath("id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("name").value(savedAppUser.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("email").value(appUser1.getEmail())
        );
    }

    @Test
    public void testDeleteAppUser() throws Exception {
        // create user
        AppUser appUser = TestDataUtil.createTestUser();
        AppUser savedAppUser = appUserService.save(appUser);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + savedAppUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }


}
