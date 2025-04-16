package com.brightkut.userservice.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.brightkut.commonlib.lib.uuid.UuidUtils;

public class TokenUtil {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateSaltToken(int length) {
        try {

            // SecureRandom instance for cryptographic strength random number generation
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            StringBuilder token = new StringBuilder(length);

            // Generate random alphanumeric string
            for (int i = 0; i < length; i++) {
                int randomIndex = secureRandom.nextInt(ALPHANUMERIC.length());
                token.append(ALPHANUMERIC.charAt(randomIndex));
            }

            // Get the current timestamp in nanoseconds for even more uniqueness
            long timestamp = System.nanoTime();
            String uniqueId = UuidUtils.generateUUID().toString().replace("-", "").substring(0, 8);

            // Combine all parts: random string + timestamp + unique UUID part
            String saltToken = token.toString() + timestamp + uniqueId;

            return saltToken;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No secure random algorithm available", e);
        }
    }


}
