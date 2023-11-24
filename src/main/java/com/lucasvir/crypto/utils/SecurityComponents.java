package com.lucasvir.crypto.utils;

import com.lucasvir.crypto.exceptions.EncryptionFaildException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class SecurityComponents {

    @Value("${cryptography.key}")
    private String KEY;

    public String encrypt(String rawData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedTextBytes = cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedTextBytes);
        } catch (Exception e) {
            throw new EncryptionFaildException("Encryption fail." + "error:" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedTextBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedTextBytes);
        } catch (Exception e) {
            throw new EncryptionFaildException("Decryption fail." + "error:" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
