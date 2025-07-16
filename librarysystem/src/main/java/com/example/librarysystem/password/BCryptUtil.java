package com.example.librarysystem.password;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    // 비밀번호 암호화
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 비밀번호 검증
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hashed password");
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
