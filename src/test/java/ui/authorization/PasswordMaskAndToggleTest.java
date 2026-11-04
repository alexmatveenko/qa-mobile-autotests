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
class PasswordMaskAndToggleTest extends UiBaseTest {

    @Test
    @Story("Поле пароля")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Пароль маскируется и показывается по иконке")
    @Description("ТЗ: символы пароля маскируются «•»; нечётное нажатие на значок показывает текст, чётное - снова маскирует.")
    void passwordMaskAndToggle() {
        loginPage.waitOpened();

        loginPage.typePassword(Users.validPassword());
        assertThat(loginPage.isPasswordMasked())
                .as("После ввода пароль должен быть замаскирован")
                .isTrue();

        loginPage.togglePasswordVisibility();
        assertThat(loginPage.isPasswordMasked()).isFalse();
        assertThat(loginPage.passwordValue()).isEqualTo(Users.validPassword());

        loginPage.togglePasswordVisibility();
        assertThat(loginPage.isPasswordMasked()).isTrue();
    }
}
