package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import utils.Config;

import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.time.Duration;

public final class Driver {

    private Driver() {
    }

    public static AndroidDriver create() {
        if (!Files.exists(Config.appPath())) {
            throw new IllegalStateException(
                    "APK not found: " + Config.appPath()
                            + ". Put the built Alfa-Test apk to app/alfatest.apk or set APP_PATH.");
        }

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(Config.platformName())
                .setAutomationName(Config.automationName())
                .setDeviceName(Config.deviceName())
                .setApp(Config.appPath().toString())
                .setAppPackage(Config.appPackage())
                .setAppActivity(Config.appActivity())
                .setAutoGrantPermissions(true)
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(120));

        Config.platformVersion().ifPresent(options::setPlatformVersion);

        try {
            AndroidDriver driver = new AndroidDriver(URI.create(Config.appiumUrl()).toURL(), options);
            driver.manage().timeouts().implicitlyWait(Config.implicitWait());
            return driver;
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium URL: " + Config.appiumUrl(), e);
        }
    }
}
