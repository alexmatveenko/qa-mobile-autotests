package utils;

import java.util.regex.Pattern;

public final class LoginRules {

    public static final int MAX_LENGTH = 50;

    public static final Pattern LOGIN_ALLOWED =
            Pattern.compile("^[A-Za-z .,/'_-]{1," + MAX_LENGTH + "}$");

    private LoginRules() {
    }

    public static boolean isAllowedLogin(String value) {
        return value != null && LOGIN_ALLOWED.matcher(value).matches();
    }

    public static String repeat(char symbol, int length) {
        return String.valueOf(symbol).repeat(length);
    }
}
