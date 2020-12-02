package com.example.bandproject.demo.controllers;

import com.auth0.jwt.JWT;
import com.example.bandproject.demo.configurations.SecurityConstants;
import com.example.bandproject.demo.models.CheckPassword;
import com.example.bandproject.demo.models.UpdateUser;
import com.example.bandproject.demo.models.User;
import com.example.bandproject.demo.models.VerifySecurityAnswer;
import com.example.bandproject.demo.repositories.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    public static final String API_V_1_USERS = "/api/v1/users/";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final ObjectMapper mapper = new ObjectMapper();


    public String JWTTokeCreator(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        return token;
    }


    /////////////////GetUser//////////////////////
    @Test
    @Order(1)
    void GetUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].username", is("derToaster1412")))
                .andExpect(jsonPath("$.content.[1].username", is("Zockerlady")))
                .andExpect(jsonPath("$.content.[6].username", is("Soerschaden")));
    }

    /////////////////Search//////////////////////
    @Test
    @Order(2)
    void Search() throws Exception {
        String keyword = "derToas";
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "search/" + keyword))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "search/" + keyword).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].username", is("derToaster1412")));
    }


    /////////////////GetUserById//////////////////////
    @Test
    @Order(3)
    void GetUserById() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + 1))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + 1).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("derToaster1412")));
    }


    /////////////////UpdateUser//////////////////////
    @Test
    @Order(4)
    void UpdateUser() throws Exception {

        assertEquals("yadeyade@blalba.de", userRepository.findById(3L).get().getEmail());
        assertTrue(passwordEncoder.matches("user", userRepository.findById(3L).get().getPassword()));
        ObjectMapper objectMapper = new ObjectMapper();
        UpdateUser updateUser = new UpdateUser(3L, "newPass", "new@Mail.com");
        String jsonUpdateUser = objectMapper.writeValueAsString(updateUser);
        mvc.perform(MockMvcRequestBuilders.put(API_V_1_USERS).content(jsonUpdateUser))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.put(API_V_1_USERS).contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + token).content(jsonUpdateUser))
                .andExpect(status().isOk());

        assertEquals("new@Mail.com", userRepository.findById(3L).get().getEmail());
        assertTrue(passwordEncoder.matches("newPass", userRepository.findById(3L).get().getPassword()));
    }

    /////////////////GetAll//////////////////////
    @Test
    @Order(5)
    void GetAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "all"))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "all").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].username", is("derToaster1412")))
                .andExpect(jsonPath("$.[6].username", is("Soerschaden")));


    }

    /////////////////CheckPassword//////////////////////
    @Test
    @Order(6)
    void CheckPassword() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "checkpw"))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        CheckPassword checkPassword = new CheckPassword(3L, "user");
        String jsonCheckPassword = mapper.writeValueAsString(checkPassword);
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "checkpw")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCheckPassword))
                .andExpect(status().isOk())
                .equals(true);
        CheckPassword checkPasswordFalse = new CheckPassword(3L, "blabla");
        String jsonCheckPasswordFalse = mapper.writeValueAsString(checkPasswordFalse);
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "checkpw")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCheckPasswordFalse))
                .andExpect(status().isOk())
                .equals(false);
    }

    /////////////////AddUser//////////////////////
    @Test
    @Order(7)
    void AddUser() throws Exception {

        User user = new User("newUser", "newPassword", "are you stupid?", "yep");
        String jsonUser = mapper.writeValueAsString(user);
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser))
                .andExpect(status().isOk());
        assertTrue(userRepository.findUserByUsername(user.getUsername()).isPresent());
        assertTrue(passwordEncoder.matches(user.getPassword(), userRepository.findUserByUsername(user.getUsername()).get().getPassword()));
        assertTrue(passwordEncoder.matches(user.getSecurityAnswer(), userRepository.findUserByUsername(user.getUsername()).get().getSecurityAnswer()));

    }

    /////////////////GetByUsername//////////////////////
    @Test
    @Order(8)
    void GetByUsername() throws Exception {
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "get/" + "user")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user")));
    }

    /////////////////Admin//////////////////////
    @Test
    @Order(9)
    void Admin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "admin"))
                .andExpect(status().isForbidden());
        String userToken = JWTTokeCreator("user");
        String adminToken = JWTTokeCreator("admin");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "admin")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "admin")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());


    }

    /////////////////User//////////////////////
    @Test
    @Order(10)
    void User() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "user"))
                .andExpect(status().isForbidden());
        String userToken = JWTTokeCreator("user");
        String adminToken = JWTTokeCreator("admin");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "user")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "user")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    /////////////////VerifyAnswer//////////////////////
    @Test
    @Order(11)
    void VerifyAnswer() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "verifyanswer"))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        VerifySecurityAnswer answer = new VerifySecurityAnswer(3L, "yep");
        VerifySecurityAnswer answerFalse = new VerifySecurityAnswer(3L, "nope");
        String jsonAnswer = mapper.writeValueAsString(answer);
        String jsonAnswerFalse = mapper.writeValueAsString(answerFalse);
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "verifyanswer")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAnswer))
                .andExpect(status().isOk())
                .equals(true);

        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "verifyanswer")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAnswerFalse))
                .andExpect(status().isOk())
                .equals(false);
        assertTrue(passwordEncoder.matches(answer.getAnswer(), userRepository.findById(answer.getUserId()).get().getSecurityAnswer()));
    }

    /////////////////ApproveUser//////////////////////
    @Test
    @Order(12)
    void ApproveUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "approve/" + 3L))
                .andExpect(status().isForbidden());

        String userToken = JWTTokeCreator("user");

        String adminToken = JWTTokeCreator("admin");

        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "approve/" + 3L)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());

        assertFalse(userRepository.findById(3L).get().getApproved());

        mvc.perform(MockMvcRequestBuilders.post(API_V_1_USERS + "approve/" + 3L)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        assertTrue(userRepository.findById(3L).get().getApproved());

    }

    /////////////////IsApproved//////////////////////
    @Test
    @Order(13)
    void IsApproved() throws Exception {
        User user = userRepository.findUserByUsername("user").get();
        user.setApproved(false);
        userRepository.save(user);
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "approved/" + "user"))
                .andExpect(status().isForbidden());

        String userToken = JWTTokeCreator("user");

        String adminToken = JWTTokeCreator("admin");

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "approved/" + "user")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
        assertFalse(userRepository.findUserByUsername("user").get().getApproved());
        assertTrue(userRepository.findUserByUsername("admin").get().getApproved());

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "approved/" + "user")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .equals(false);

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "approved/" + "admin")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .equals(true);


    }

    /////////////////IsAdmin//////////////////////
    @Test
    @Order(14)
    void IsAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isAdmin/" + "user"))
                .andExpect(status().isForbidden());

        String userToken = JWTTokeCreator("user");

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isAdmin/" + "user")
        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .equals(false);

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isAdmin/" + "admin")
        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .equals(true);

    }

    /////////////////IsRegistered//////////////////////
    @Test
    @Order(15)
    void IsRegistered() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isRegistered/" + "user"))
                .andExpect(status().isForbidden());

        String userToken = JWTTokeCreator("user");

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isRegistered/" + "user")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .equals(true);
        assertTrue(userRepository.findUserByUsername("user").isPresent());

        mvc.perform(MockMvcRequestBuilders.get(API_V_1_USERS + "isRegistered/" + "MrNothington")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .equals(false);
        assertFalse(userRepository.findUserByUsername("MrNothington").isPresent());

    }
    /////////////////DeleteOne//////////////////////

    @Test
    @Order(16)
    void DeleteOne() throws Exception {
        assertTrue(userRepository.findUserByUsername("user").isPresent());
        mvc.perform(MockMvcRequestBuilders.delete(API_V_1_USERS + 3))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.delete(API_V_1_USERS + 3).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
        assertFalse(userRepository.findUserByUsername("user").isPresent());

    }
}
