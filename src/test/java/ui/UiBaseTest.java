package ui;

import base.BaseTest;
import base.Driver;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import pages.LoginPage;
import pages.SuccessPage;
import utils.Config;

public abstract class UiBaseTest extends BaseTest {

    protected AndroidDriver driver;
    protected LoginPage loginPage;
    protected SuccessPage successPage;

    @BeforeEach
    void setUpUi() {
        Assumptions.assumeTrue(
                java.nio.file.Files.exists(Config.appPath()),
                "APK не найден: " + Config.appPath() + ". Положите файл в app/alfatest.apk");
        driver = Driver.create();
        DRIVERS.set(driver);
        loginPage = new LoginPage(driver);
        successPage = new SuccessPage(driver);
        Allure.parameter("устройство", Config.deviceName());
        Allure.parameter("appium", Config.appiumUrl());
    }

    @AfterEach
    void tearDownUi() {
        AndroidDriver current = DRIVERS.get();
        if (current != null) {
            current.quit();
        }
        DRIVERS.remove();
    }
}
