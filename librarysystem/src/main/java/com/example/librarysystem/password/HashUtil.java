package com.example.librarysystem.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString(); // 64자리 해시 문자열
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 암호화 실패", e);
        }
    }

    public static boolean matchPassword(String inputPassword, String hashedPassword) {
        return hashPassword(inputPassword).equals(hashedPassword);
    }
}
