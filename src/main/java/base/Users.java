package base;

import utils.Config;

/**
 * Учётные данные для сценариев (значения из {@link Config} / config.properties).
 */
public final class Users {

    private Users() {
    }

    public static String validLogin() {
        return Config.validLogin();
    }

    public static String validPassword() {
        return Config.validPassword();
    }
}
