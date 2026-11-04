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
import utils.LoginRules;
import utils.Requirements;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Alfa-Test")
@Feature("Авторизация")
class DisallowedLoginSymbolShowsInvalidValueTest extends UiBaseTest {

    @Test
    @Story("Валидация логина")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Недопустимый символ в логине - InvalidValue")
    @Description("ТЗ: при вводе неразрешённых символов отображается InvalidValue. Regex: логин вне LOGIN_ALLOWED.")
    void disallowedLoginSymbolShowsInvalidValue() {
        String invalidLogin = "Login@";
        assertThat(invalidLogin).doesNotMatch(LoginRules.LOGIN_ALLOWED);

        loginPage.waitOpened();
        loginPage.typeUsername(invalidLogin);
        loginPage.typePassword(Users.validPassword());
        loginPage.tapLogin();

        attachText("Фактическое сообщение", loginPage.errorText());
        assertThat(loginPage.errorText())
                .as("DEFECT-3/5: ожидается InvalidValue по ТЗ")
                .matches(Requirements.INVALID_VALUE_MESSAGE);
    }
}
