package com.bly.fintech.util;

import java.util.regex.Pattern;

public class Utils {

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
