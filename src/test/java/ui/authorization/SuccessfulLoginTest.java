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
import utils.Requirements;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Alfa-Test")
@Feature("Авторизация")
class SuccessfulLoginTest extends UiBaseTest {

    @Test
    @Story("Успешный вход")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешный вход с корректными учётными данными")
    @Description("ТЗ: Login и Password → переход на экран «Вход в Alfa-Test выполнен».")
    void successfulLoginWithValidCredentials() {
        loginPage.waitOpened();
        assertThat(loginPage.title()).matches(Requirements.LOGIN_TITLE);

        loginPage.login(Users.validLogin(), Users.validPassword());

        String successTitle = successPage.waitOpened().title();
        attachText("Заголовок экрана успеха", successTitle);
        assertThat(successTitle).matches(Requirements.SUCCESS_TITLE);
    }
}
