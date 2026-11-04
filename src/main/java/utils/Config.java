package utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Optional;
import java.util.Properties;

public final class Config {

    private static final Properties PROPS = load();

    private Config() {
    }

    public static String appiumUrl() {
        return value("APPIUM_URL", "appium.url");
    }

    public static String platformName() {
        return value("PLATFORM_NAME", "platform.name");
    }

    public static String automationName() {
        return value("AUTOMATION_NAME", "automation.name");
    }

    public static String deviceName() {
        return value("DEVICE_NAME", "device.name");
    }

    public static Optional<String> platformVersion() {
        return optionalValue("PLATFORM_VERSION", "platform.version");
    }

    public static Path appPath() {
        return Path.of(value("APP_PATH", "app.path")).toAbsolutePath();
    }

    public static String appPackage() {
        return value("APP_PACKAGE", "app.package");
    }

    public static String appActivity() {
        return value("APP_ACTIVITY", "app.activity");
    }

    public static Duration implicitWait() {
        return Duration.ofSeconds(Long.parseLong(value("IMPLICIT_WAIT_SECONDS", "implicit.wait.seconds")));
    }

    public static Duration explicitWait() {
        return Duration.ofSeconds(Long.parseLong(value("EXPLICIT_WAIT_SECONDS", "explicit.wait.seconds")));
    }

    public static String validLogin() {
        return value("VALID_LOGIN", "valid.login");
    }

    public static String validPassword() {
        return value("VALID_PASSWORD", "valid.password");
    }

    private static Optional<String> optionalValue(String envKey, String propertyKey) {
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return Optional.of(fromEnv);
        }
        String fromSystem = System.getProperty(propertyKey);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return Optional.of(fromSystem);
        }
        String fromFile = PROPS.getProperty(propertyKey);
        if (fromFile == null || fromFile.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(fromFile);
    }

    private static String value(String envKey, String propertyKey) {
        String fromEnv = System.getenv(envKey);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        String fromSystem = System.getProperty(propertyKey);
        if (fromSystem != null && !fromSystem.isBlank()) {
            return fromSystem;
        }
        String fromFile = PROPS.getProperty(propertyKey);
        if (fromFile == null || fromFile.isBlank()) {
            throw new IllegalStateException("Missing config value: " + propertyKey);
        }
        return fromFile;
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream stream = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (stream == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read config.properties", e);
        }
        return properties;
    }
}
