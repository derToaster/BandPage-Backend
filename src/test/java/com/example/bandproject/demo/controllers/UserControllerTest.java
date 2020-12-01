package com.example.bandproject.demo.controllers;

import com.auth0.jwt.JWT;
import com.example.bandproject.demo.configurations.SecurityConstants;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    String token;

    @Autowired
    private MockMvc mvc;


    public String JWTTokeCreator( String username){
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        return token;
    }

    @BeforeEach
    public void setup() throws Exception {

    }
    /////////////////GetUser//////////////////////
    @Test
    void GetUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/"))
                .andExpect(status().isForbidden());
       String token =  JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());


    }

    /////////////////Search//////////////////////
    @Test
    void Search() {
        fail("needs to be implemented");
    }

    /////////////////Create//////////////////////
    @Test
    void Create() {
        fail("needs to be implemented");
    }

    /////////////////GetUserById//////////////////////
    @Test
    void GetUserById() {
        fail("needs to be implemented");
    }

    /////////////////DeleteOne//////////////////////
    @Test
    void DeleteOne() {
        fail("needs to be implemented");
    }

    /////////////////ShowPage//////////////////////
    @Test
    void ShowPage() {
        fail("needs to be implemented");
    }

    /////////////////UpdateUser//////////////////////
    @Test
    void UpdateUser() {
        fail("needs to be implemented");
    }

    /////////////////GetAll//////////////////////
    @Test
    void GetAll() {
        fail("needs to be implemented");
    }

    /////////////////CheckPassword//////////////////////
    @Test
    void CheckPassword() {
        fail("needs to be implemented");
    }

    /////////////////AddUser//////////////////////
    @Test
    void AddUser() {
        fail("needs to be implemented");
    }

    /////////////////GetByUsername//////////////////////
    @Test
    void GetByUsername() {
        fail("needs to be implemented");
    }

    /////////////////Admin//////////////////////
    @Test
    void Admin() {
        fail("needs to be implemented");
    }

    /////////////////User//////////////////////
    @Test
    void User() {
        fail("needs to be implemented");
    }

    /////////////////VerifyAnswer//////////////////////
    @Test
    void VerifyAnswer() {
        fail("needs to be implemented");
    }

    /////////////////ApproveUser//////////////////////
    @Test
    void ApproveUser() {
        fail("needs to be implemented");
    }

    /////////////////IsApproved//////////////////////
    @Test
    void IsApproved() {
        fail("needs to be implemented");
    }

    /////////////////IsAdmin//////////////////////
    @Test
    void IsAdmin() {
        fail("needs to be implemented");
    }

    /////////////////IsRegistered//////////////////////
    @Test
    void IsRegistered() {
        fail("needs to be implemented");
    }
}
