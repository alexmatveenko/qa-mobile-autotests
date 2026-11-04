package ui.authorization;

import base.Users;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.UiBaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Alfa-Test")
@Feature("Авторизация")
class InvalidPasswordDoesNotOpenSuccessScreenTest extends UiBaseTest {

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Неверный пароль не открывает экран успеха")
    @Description("ТЗ: при неуспешном authorize переход на экран «Вход в Alfa-Test выполнен» не выполняется.")
    void invalidPasswordDoesNotOpenSuccessScreen() {
        loginPage.waitOpened();
        loginPage.login(Users.validLogin(), "WrongPassword");

        assertThat(driver.getPageSource()).doesNotContain("Вход в Alfa-Test выполнен");
        assertThat(loginPage.errorText())
                .as("После неуспешной авторизации должно отображаться сообщение об ошибке")
                .isNotBlank();
    }
}
