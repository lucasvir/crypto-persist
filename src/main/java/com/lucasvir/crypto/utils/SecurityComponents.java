package com.lucasvir.crypto.utils;

import org.springframework.stereotype.Component;

import javax.crypto.*;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class SecurityComponents {

    private final SecretKey KEY = generateKey();

    public String encrypt(String rawData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KEY);
            byte[] encryptedTextBytes = cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedTextBytes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decryptedTextBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedTextBytes);
        } catch (Exception e) {
            throw new RuntimeException("Descryptography fail." + "error:" + e.getMessage());
        }
    }

    public static SecretKey generateKey() {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
            keygenerator.init(256);
            return keygenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("error: " + e.getMessage());
        }
    }
}
