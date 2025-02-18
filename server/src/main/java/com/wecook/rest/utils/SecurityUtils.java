package com.wecook.rest.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {
    public static  String sha256(String password){
        String hashString;

        try {
            MessageDigest messageD = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageD.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

            hashString = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return hashString;
    }

    public static String extractMimeType(String base64Image) {
        int startIndex = base64Image.indexOf(":") + 1;
        int endIndex = base64Image.indexOf(";");
        if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
            return null;
        }
        return base64Image.substring(startIndex, endIndex);
    }

    public static String getMimeType(byte[] data) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            return URLConnection.guessContentTypeFromStream(bais);
        }
    }
}
