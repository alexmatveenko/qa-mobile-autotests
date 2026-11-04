package base;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@ExtendWith({BaseTest.AllureWatcher.class, AllureDisplayNameAsSuiteExtension.class})
public abstract class BaseTest {

    protected static final ThreadLocal<AndroidDriver> DRIVERS = new ThreadLocal<>();

    @Attachment(value = "Скриншот", type = "image/png")
    protected static byte[] screenshot(AndroidDriver driver) {
        return driver.getScreenshotAs(OutputType.BYTES);
    }

    protected static void attachText(String name, String value) {
        Allure.addAttachment(name, "text/plain",
                new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)), ".txt");
    }

    public static class AllureWatcher implements TestWatcher {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            AndroidDriver driver = DRIVERS.get();
            if (driver == null) {
                return;
            }
            Allure.addAttachment("Скриншот при падении", "image/png",
                    new ByteArrayInputStream(screenshot(driver)), ".png");
            Allure.addAttachment("Исходный код страницы", "text/xml",
                    driver.getPageSource(), ".xml");
            Allure.addAttachment("Ошибка", "text/plain",
                    Optional.ofNullable(cause.getMessage()).orElse("неизвестно"),
                    ".txt");
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            AndroidDriver driver = DRIVERS.get();
            if (driver != null) {
                Allure.addAttachment("Скриншот", "image/png",
                        new ByteArrayInputStream(screenshot(driver)), ".png");
            }
        }
    }
}
