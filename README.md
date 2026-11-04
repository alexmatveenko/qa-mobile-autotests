# Автотесты авторизации Alfa-Test

Тестовое задание на базе приложения [lunin-vadim/qa-mobile](https://github.com/lunin-vadim/qa-mobile) (исходники и «Требования работы приложения.docx» — в том репозитории).

**Стек:** Java 17, Appium (UiAutomator2), JUnit 5, Allure, Page Object.  
**Учётные данные:** `Login` / `Password`.

> **Локаторы:** в ТЗ указаны CSS и XPath. По **согласованию с HR** в проекте используются **только XPath** (native Android не поддерживает CSS; согласование зафиксировано до сдачи).

## Содержимое репозитория

| Компонент | Путь |
| --- | --- |
| Автотесты | `src/`, `pom.xml`, `mvnw` / `mvnw.cmd` |
| APK для прогона тестов | `app/alfatest.apk` |
| Дефекты приложения | [docs/defects.md](docs/defects.md) |
| Готовый отчёт Allure (HTML) | [docs/allure-report/index.html](docs/allure-report/index.html) |

## Структура проекта

Плоские пакеты `base`, `pages`, `utils`. UI-тесты — в `ui/authorization`.

```
app/
  alfatest.apk              APK, который ставят тесты

src/main/java/
  base/                     Драйвер, тестовые пользователи
    Driver.java
    Users.java
  pages/                    Page Object
    BasePage.java
    LoginPage.java
    SuccessPage.java
  utils/                    Конфиг и константы ТЗ
    Config.java             config.properties + env
    Requirements.java       строки и Pattern для assert по ТЗ
    LoginRules.java

src/main/resources/
  config.properties

src/test/java/
  base/BaseTest.java        Allure-вложения
  ui/UiBaseTest.java        инициализация драйвера и Page Object
  ui/authorization/         пять UI-сценариев Alfa-Test
    SuccessfulLoginTest.java
    InvalidPasswordDoesNotOpenSuccessScreenTest.java
    LoginFieldLengthBoundariesTest.java
    DisallowedLoginSymbolShowsInvalidValueTest.java
    PasswordMaskAndToggleTest.java

src/test/resources/
  allure.properties
  categories.json
```

## Подход к тестированию

- Для прогона используется **`app/alfatest.apk`**: сборка upstream с единственным исправлением **DEFECT-1** (см. [docs/defects.md](docs/defects.md)). Поведение приложения **не менялось**.
- UI-автотесты сверяют экран с **требованиями ТЗ** (`Requirements.java`). **Падающий тест** = расхождение с ТЗ; описание в [docs/defects.md](docs/defects.md) и в Allure (`@Description`, вложения «Фактическое сообщение»).
- Ожидаемо **зелёные** сценарии на текущей сборке: успешный вход, неверный пароль (без перехода на успех), маска пароля. **Красные** - расхождения, явно названные в ТЗ (50 символов, InvalidValue).
- После `mvnw clean test` Maven может завершиться с **BUILD FAILURE**: **2** из **7** прогонов падают осознанно (DEFECT-4, DEFECT-3/5). Это не ошибка установки автотестов.

## Пять UI-автотестов

| № | Сценарий | Формулировка в ТЗ | Дефект при падении |
| --- | --- | --- | --- |
| 1 | Успешный вход | Login/Password → экран «Вход в Alfa-Test выполнен» | - |
| 2 | Неверный пароль | Нет перехода после неуспешного authorize | - |
| 3 | Длина логина | Max **50** при вводе; границы **49 / 50 / 51** (`@ParameterizedTest`) | DEFECT-4 |
| 4 | Символ в логине | Неразрешённый символ → **InvalidValue** | DEFECT-3, DEFECT-5 |
| 5 | Пароль | Маска «•» и переключение по иконке | - |

**Regex** используется в UI-тестах: заголовки экранов (`Pattern`), допустимый логин по ТЗ (`LoginRules.LOGIN_ALLOWED`), сообщение `InvalidValue`.

## Требования к окружению

1. JDK 17 (`JAVA_HOME`)
2. Android SDK, запущенный эмулятор или устройство (`adb devices`)
3. Appium 2+ и драйвер UiAutomator2:

```bash
npm install -g appium
appium driver install uiautomator2
appium
```

4. При необходимости - параметры в `src/main/resources/config.properties` или переменные: `APP_PATH`, `APPIUM_URL`, `DEVICE_NAME`, `PLATFORM_VERSION`.

Рекомендуется клонировать репозиторий в путь **без кириллицы** (например `C:\work\alfa-test-appium`).

## Запуск

Из **корня** репозитория (где `pom.xml`):

```bash
.\mvnw.cmd clean test
.\mvnw.cmd allure:serve
```

Перед тестами: Appium на `http://127.0.0.1:4723` (по умолчанию в `config.properties`) и устройство `emulator-5554`, если не задано иное.

Статический отчёт в репозитории: **`docs/allure-report/`**.  
**Не открывайте `index.html` через `file://`** — браузер блокирует JSON, экран зависает на «Loading…».

Просмотр локально: после свежего прогона `.\mvnw.cmd allure:serve` (из `target/allure-results`).

Обновить HTML в репозитории: `.\mvnw.cmd test` → `.\mvnw.cmd allure:report` → скопировать `target/site/allure-maven-plugin` в `docs/allure-report`.

## Паттерны и отчётность

- Page Object (`pages/`), конфиг и ТЗ (`utils/`), драйвер (`base/Driver`), UI-тесты (`ui/`).
- Allure: `@Epic`, `@Feature`, `@Story`, шаги `@Step` на русском языке.

## Приложение и дефекты

Единственное изменение в APK относительно «сломанной» сборки upstream - **DEFECT-1** в [docs/defects.md](docs/defects.md). Остальные расхождения с ТЗ **не исправлялись**; их фиксируют падающие автотесты и отчёт Allure.
