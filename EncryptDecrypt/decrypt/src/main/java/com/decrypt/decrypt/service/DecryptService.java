package com.decrypt.decrypt.service;

import org.springframework.stereotype.Service;

@Service
public class DecryptService {

    public String decrypt(final String str, final Integer key) {
        String decryptedString = "";

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if ((int)ch >= 32 && (int)ch <= 127) {
                int newAsciiValue = (int)ch - key;
                if(newAsciiValue < 32) {
                    newAsciiValue += 95;
                }
                if(newAsciiValue > 127) {
                    newAsciiValue -= 95;
                }
                decryptedString += (char)newAsciiValue;
            }
        }
        return decryptedString;
    }
}
