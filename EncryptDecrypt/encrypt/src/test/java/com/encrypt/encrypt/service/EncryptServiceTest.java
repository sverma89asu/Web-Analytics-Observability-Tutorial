package com.encrypt.encrypt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EncryptServiceTest {

    @Autowired
    EncryptService encryptService;

    @Test
    void testEncrypt() {
        final String output = encryptService.encrypt("hi", 100);

        assertThat(output).isEqualTo("mn");
    }

    @Test
    void testEncrypt2() {
        final String output = encryptService.encrypt("LM 4#", 4);

        assertThat(output).isEqualTo("PQ$8'");
    }
}
