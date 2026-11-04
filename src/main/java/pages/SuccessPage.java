package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SuccessPage extends BasePage {

    private static final By SUCCESS_TITLE = AppiumBy.xpath(
            "//android.widget.TextView[contains(@text,'Вход в Alfa-Test выполнен')]");

    public SuccessPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Дождаться экрана успешного входа")
    public SuccessPage waitOpened() {
        visible(SUCCESS_TITLE);
        return this;
    }

    @Step("Прочитать заголовок экрана успеха")
    public String title() {
        return textOf(SUCCESS_TITLE);
    }
}
