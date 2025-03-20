package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    public void login(DataHelper.AuthInfo authInfo) {
        $("[data-test-id=login] input").setValue(authInfo.getLogin());
        $("[data-test-id=password] input").setValue(authInfo.getPassword());
        $("[data-test-id=action-login]").click();
    }

    public void checkErrorNotification() {
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно указан логин или пароль"));
    }
}
