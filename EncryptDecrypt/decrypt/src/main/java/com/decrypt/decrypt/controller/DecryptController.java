package com.decrypt.decrypt.controller;

import com.decrypt.decrypt.service.DecryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DecryptController {

    @Autowired
    DecryptService decryptService;

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam(required = false) String text,
                          @RequestParam(required = false) Integer key) {
        if (text == null && key == null) {
            throw new RuntimeException("Text and key not passed as query parameter");
        }
        if (text ==  null) {
            throw new RuntimeException("Text not passed as query parameter");
        }
        if (key == null) {
            throw new RuntimeException("Key not passed as query parameter");
        }
        log.info("Received DECRYPT request with text " + text + " and key " + key);
        return decryptService.decrypt(text, key);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(final Exception exception) {
        log.error("Found an exception with message: {}", exception.getMessage());
        return exception.getMessage();
    }
}
