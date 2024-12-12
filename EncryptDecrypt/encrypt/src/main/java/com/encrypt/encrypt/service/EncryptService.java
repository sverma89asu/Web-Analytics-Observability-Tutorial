package com.encrypt.encrypt.service;

import org.springframework.stereotype.Service;

@Service
public class EncryptService {

    public String encrypt(String str, Integer key) {
        String encryptedString = "";

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if ((int)ch >= 32 && (int)ch <= 127) {
                int newAsciiValue = (int)ch + key;
                if(newAsciiValue < 32) {
                    newAsciiValue += 95;
                }
                if(newAsciiValue > 127) {
                    newAsciiValue -= 95;
                }
                encryptedString += (char)newAsciiValue;
            }
        }
        return encryptedString;
    }
}
