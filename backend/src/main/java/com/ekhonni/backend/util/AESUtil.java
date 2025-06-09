package com.ekhonni.backend.util;

import com.ekhonni.backend.exception.InvalidVerificationTokenException;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Author: Safayet Rafi
 * Date: 06/02/25
 */

@Service
public  class AESUtil {

    private  final String ALGORITHM = "AES";
    private  final String ALGORITHM_MODE = "AES/CBC/PKCS5Padding";
    private  final int KEY_SIZE = 256;

    private  final SecretKey key = generateKey();
    private  final IvParameterSpec iv = generateIV();

    public  SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_SIZE);
            return keyGenerator.generateKey();
        }catch (Exception e){
            throw new RuntimeException("Error generating key", e);
        }
    }

    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getUrlEncoder().encodeToString(encryptedBytes);
        }catch (Exception e){
            throw new RuntimeException("Error while encrypting", e);
        }
    }

    public String decrypt(String encryptedData){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        }catch (Exception e){
            throw new RuntimeException("Error while decrypting", e);
        }
    }

    public IvParameterSpec generateIV(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public String extractEmail(String token) {
        try {
            String decryptedString = decrypt(token);
            String[] parts = decryptedString.split(":");
            if (parts.length != 2) {
                throw new InvalidVerificationTokenException("Invalid Verification Token");
            }
            return parts[1];
        } catch (Exception e) {
            throw new InvalidVerificationTokenException("Invalid Verification Token");
        }
    }
}
