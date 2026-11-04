package utils;

import java.util.regex.Pattern;

/**
 * Ожидаемое поведение по документу «Требования работы приложения» (ТЗ).
 * Строки и regex для проверок UI — один источник для тестов и документации дефектов.
 */
public final class Requirements {

    public static final int MAX_LOGIN_LENGTH = 50;

    public static final String BUTTON_ENTER = "Войти";
    public static final String INVALID_VALUE = "InvalidValue";
    public static final String EXCEPT_VALUE = "ExceptValue";
    public static final String INCORRECT_CREDENTIALS = "Введены неверные данные";

    public static final Pattern LOGIN_TITLE = Pattern.compile("^Вход в Alfa-Test$");
    public static final Pattern SUCCESS_TITLE = Pattern.compile("Вход в Alfa-Test выполнен");
    public static final Pattern INVALID_VALUE_MESSAGE =
            Pattern.compile("^" + Pattern.quote(INVALID_VALUE) + "$");

    private Requirements() {
    }
}
