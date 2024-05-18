package org.example.userservice.core.security;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class AES256 {
    @Value("${secret.AES256}")
    private String privateKey_256;

    private SecretKeySpec secretKey;

    public String aesCBCEncode(String plainText) {
        try {
            secretKey = new SecretKeySpec(privateKey_256.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec IV = new IvParameterSpec(privateKey_256.substring(0, 16).getBytes(StandardCharsets.UTF_8));

            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, secretKey, IV);

            byte[] encryptionByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Hex.encodeHexString(encryptionByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String aesCBCDecode(String encodeText) {
        try {
            secretKey = new SecretKeySpec(privateKey_256.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec IV = new IvParameterSpec(privateKey_256.substring(0, 16).getBytes(StandardCharsets.UTF_8));

            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, secretKey, IV);

            byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());

            return new String(c.doFinal(decodeByte), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean matches(String plainText, String encodedText) {
        try {
            String encodedPlainText = aesCBCEncode(plainText);
            return encodedPlainText.equals(encodedText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

