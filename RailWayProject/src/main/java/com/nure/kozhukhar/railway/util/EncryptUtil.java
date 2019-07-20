package com.nure.kozhukhar.railway.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Encrypt Util.
 * Works with String and hashing it to the MD5
 *
 * @author Anatol Kozhukhar
 */
public class EncryptUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String hash(String str) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MessageDigest digest;
        StringBuilder hexString = new StringBuilder();
        digest = MessageDigest.getInstance("MD5");
        digest.update(str.getBytes(StandardCharsets.UTF_8));
        for (byte d : digest.digest()) {
            hexString.append(getFirstHexDigit(d)).append(getSecondHexDigit(d));
        }
        return hexString.toString();
    }

    private static char getFirstHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) / 16];
    }

    private static char getSecondHexDigit(byte x) {
        return HEX_DIGITS[(0xFF & x) % 16];
    }
}
