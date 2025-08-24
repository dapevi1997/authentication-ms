package co.com.crediya.model.user.utils;

import java.util.regex.Pattern;

import static co.com.crediya.model.user.utils.Constantes.EMAIL_BAD_FORMAT;

public class UtilUsers {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private UtilUsers() {}

    public static void verifyEmailFormat(String email){
         if (!EMAIL_PATTERN.matcher(email).matches()){
             throw new IllegalArgumentException(EMAIL_BAD_FORMAT);
         }
    }
}
