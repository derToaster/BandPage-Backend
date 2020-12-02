package com.example.bandproject.demo.controllers;

import com.auth0.jwt.JWT;
import com.example.bandproject.demo.configurations.SecurityConstants;
import com.example.bandproject.demo.models.Band;
import com.example.bandproject.demo.repositories.BandRepository;
import com.example.bandproject.demo.repositories.UserRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BandControllerTest {
    public static final String API_V_1_BANDS = "/api/v1/bands";
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    MockMvc mvc;

    public String JWTTokeCreator(String username) {
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        return token;
    }

    /////////////////CreateBand//////////////////////
    @Test
    @Order(1)
    void CreateBand() throws Exception {
        Band band = new Band("newBand", "TestMetal", 12, false);
        String jsonBand = mapper.writeValueAsString(band);
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_BANDS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBand))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.post(API_V_1_BANDS)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBand))
                .andExpect(status().isOk());
        assertTrue(bandRepository.findBandByName("newBand").isPresent());
    }

    /////////////////DeleteBand//////////////////////
    @Test
    @Order(2)
    void DeleteBand() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(API_V_1_BANDS + "/" + bandRepository.findBandByName("newBand").get().getId()))
                .andExpect(status().isForbidden());
        assertTrue(bandRepository.findBandByName("newBand").isPresent());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.delete(API_V_1_BANDS + "/" + bandRepository.findBandByName("newBand").get().getId())
        .header("Authorization" , "Bearer " + token))
                .andExpect(status().isOk());
        assertFalse(bandRepository.findBandByName("newBand").isPresent());

    }

    /////////////////GetAllBands//////////////////////
    @Test
    @Order(3)
    void GetAllBands() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/"))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].name", is("Toaster Band")))
                .andExpect(jsonPath("$.content.[7].name", is("Egal")));

    }

    /////////////////GetAllBandsByOwners//////////////////////
    @Test
    @Order(4)
    void GetAllBandsByOwners() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/bandowners/" + 1L))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/bandowners/" + 1L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name", is(bandRepository.findBandsByOwnerId(1L).get(0).getName())));
    }

    /////////////////GetBandById//////////////////////
    @Test
    @Order(5)
    void GetBandById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS  +"/" + 1L))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/" + 1L)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(bandRepository.findById(1L).get().getName())));
    }

    /////////////////Search//////////////////////
    @Test
    @Order(6)
    void Search() throws Exception {
        String keyword = "Toa";
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS  +"/search/" + keyword))
                .andExpect(status().isForbidden());
        String token = JWTTokeCreator("user");
        mvc.perform(MockMvcRequestBuilders.get(API_V_1_BANDS + "/search/" + keyword)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.[0].name", is("Toaster Band")));
    }

}
