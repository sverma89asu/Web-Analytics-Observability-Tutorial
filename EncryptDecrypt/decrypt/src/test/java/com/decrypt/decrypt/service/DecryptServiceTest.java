package com.decrypt.decrypt.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DecryptServiceTest {

    @Autowired
    DecryptService decryptService;

    @Test
    void testDecrypt() {
        final String output = decryptService.decrypt("mn", 100);

        assertThat(output).isEqualTo("hi");
    }

    @Test
    void testDecrypt2() {
        final String output = decryptService.decrypt("PQ$8'", 4);

        assertThat(output).isEqualTo("LM 4#");
    }
}
