package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final String PKG = "com.alfabank.qapp";

    private static final By TITLE = AppiumBy.xpath(
            "//android.widget.TextView[@resource-id='" + PKG + ":id/tvTitle']");
    private static final By USERNAME = AppiumBy.xpath(
            "//*[@resource-id='" + PKG + ":id/etUsername']");
    private static final By PASSWORD = AppiumBy.xpath(
            "//*[@resource-id='" + PKG + ":id/etPassword']");
    private static final By LOGIN_BUTTON = AppiumBy.xpath(
            "//android.widget.Button[@resource-id='" + PKG + ":id/btnConfirm']");
    private static final By ERROR = AppiumBy.xpath(
            "//*[@resource-id='" + PKG + ":id/tvError']");
    private static final By LOADER = AppiumBy.xpath(
            "//*[@resource-id='" + PKG + ":id/loader']");
    private static final By PASSWORD_TOGGLE = AppiumBy.xpath(
            "//*[@resource-id='" + PKG + ":id/tilPassword']//android.widget.ImageButton");

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Дождаться открытия экрана входа")
    public LoginPage waitOpened() {
        visible(TITLE);
        visible(USERNAME);
        visible(PASSWORD);
        visible(LOGIN_BUTTON);
        return this;
    }

    @Step("Прочитать заголовок экрана входа")
    public String title() {
        return textOf(TITLE);
    }

    @Step("Ввести логин «{username}»")
    public LoginPage typeUsername(String username) {
        type(USERNAME, username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage typePassword(String password) {
        type(PASSWORD, password);
        return this;
    }

    @Step("Нажать кнопку входа")
    public LoginPage tapLogin() {
        tap(LOGIN_BUTTON);
        return this;
    }

    @Step("Авторизоваться: логин «{username}»")
    public void login(String username, String password) {
        typeUsername(username);
        typePassword(password);
        tapLogin();
    }

    @Step("Прочитать значение поля логина")
    public String usernameValue() {
        return driver.findElement(USERNAME).getText();
    }

    @Step("Прочитать текст сообщения об ошибке")
    public String errorText() {
        wait.until(d -> {
            String value = d.findElement(ERROR).getText();
            return value != null && !value.isBlank();
        });
        return driver.findElement(ERROR).getText();
    }

    @Step("Проверить, что сообщение об ошибке отображается")
    public boolean hasError() {
        String value = driver.findElement(ERROR).getText();
        return value != null && !value.isBlank();
    }

    @Step("Переключить видимость пароля")
    public LoginPage togglePasswordVisibility() {
        tap(PASSWORD_TOGGLE);
        return this;
    }

    @Step("Прочитать значение поля пароля")
    public String passwordValue() {
        return driver.findElement(PASSWORD).getText();
    }

    @Step("Проверить, что пароль замаскирован")
    public boolean isPasswordMasked() {
        String passwordAttr = driver.findElement(PASSWORD).getAttribute("password");
        return Boolean.parseBoolean(passwordAttr);
    }

    @Step("Прочитать текст кнопки входа")
    public String loginButtonText() {
        return textOf(LOGIN_BUTTON);
    }

    public boolean isLoaderVisible() {
        return isDisplayed(LOADER);
    }
}
