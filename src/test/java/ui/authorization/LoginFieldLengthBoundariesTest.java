package ui.authorization;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ui.UiBaseTest;
import utils.LoginRules;
import utils.Requirements;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Alfa-Test")
@Feature("Авторизация")
class LoginFieldLengthBoundariesTest extends UiBaseTest {

    @ParameterizedTest(name = "ввод {0} символов - в поле не больше {1}")
    @MethodSource("loginFieldLengthBoundaries")
    @Story("Валидация логина")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Граничные значения длины логина в поле (max 50 по ТЗ)")
    @Description("ТЗ, поле «Логин»: max 50 символов при вводе. Техника - граничные значения 49 / 50 / 51.")
    void loginFieldLengthBoundaries(int inputLength, int maxLengthInField) {
        Allure.parameter("длина ввода", inputLength);
        Allure.parameter("макс. в поле по сценарию", maxLengthInField);

        loginPage.waitOpened();
        String input = LoginRules.repeat('A', inputLength);
        loginPage.typeUsername(input);

        int actualLength = loginPage.usernameValue().length();
        attachText("Длина в поле", String.valueOf(actualLength));

        assertThat(actualLength)
                .as("DEFECT-4: в поле не более %d символов (ввод %d)", Requirements.MAX_LOGIN_LENGTH, inputLength)
                .isLessThanOrEqualTo(maxLengthInField);

        if (inputLength <= Requirements.MAX_LOGIN_LENGTH) {
            assertThat(actualLength)
                    .as("На границе допустимой длины введённые символы должны сохраняться в поле")
                    .isEqualTo(inputLength);
            assertThat(loginPage.usernameValue())
                    .as("Значение в поле должно соответствовать regex допустимого логина по ТЗ")
                    .matches(LoginRules.LOGIN_ALLOWED);
        }
    }

    static Stream<Arguments> loginFieldLengthBoundaries() {
        int max = Requirements.MAX_LOGIN_LENGTH;
        return Stream.of(
                Arguments.of(max - 1, max),
                Arguments.of(max, max),
                Arguments.of(max + 1, max)
        );
    }
}
