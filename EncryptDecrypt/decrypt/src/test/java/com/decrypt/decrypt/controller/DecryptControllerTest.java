package com.decrypt.decrypt.controller;

import com.decrypt.decrypt.service.DecryptService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureMockMvc
@SpringBootTest
public class DecryptControllerTest {

    @MockBean
    private DecryptService decryptService;

    @Autowired
    MockMvc mvc;

    @SneakyThrows
    @Test
    void testValidEncryption() {
        mvc.perform(MockMvcRequestBuilders.get("/decrypt?text=sample&key=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    void testException() {
        when(decryptService.decrypt(anyString(), any(Integer.class))).thenThrow(new NoSuchElementException());

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/decrypt?text=sample&key=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isBlank();
    }

    @SneakyThrows
    @Test
    void testException2() {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/decrypt?key=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull()
                .isEqualTo("Text not passed as query parameter");
    }

    @SneakyThrows
    @Test
    void testException3() {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/decrypt?text=sample")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull()
                .isEqualTo("Key not passed as query parameter");
    }

    @SneakyThrows
    @Test
    void testException4() {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/decrypt")
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotNull()
                .isEqualTo("Text and key not passed as query parameter");
    }
}
