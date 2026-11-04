package base;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Set;

/**
 * В разделе Suites Allure по умолчанию показывает FQCN класса.
 * Подменяем suite на имя теста из {@link org.junit.jupiter.api.DisplayName}.
 */
public class AllureDisplayNameAsSuiteExtension implements BeforeTestExecutionCallback {

    private static final Set<String> SUITE_GROUP_LABELS = Set.of("suite", "parentSuite", "package");

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        final String[] displayName = new String[1];
        Allure.getLifecycle().updateTestCase(result -> {
            result.getLabels().removeIf(label -> SUITE_GROUP_LABELS.contains(label.getName()));
            displayName[0] = result.getName();
        });
        if (displayName[0] != null && !displayName[0].isBlank()) {
            Allure.suite(displayName[0]);
        }
    }
}
