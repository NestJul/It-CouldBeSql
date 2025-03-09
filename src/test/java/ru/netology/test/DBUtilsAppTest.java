package ru.netology.test;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DATAHelper.*;
import static ru.netology.data.SQLHelper.getAuthCode;

class DBUtilsAppTest {
    @Test
    void shouldLoginTest() {
        String username = getValidLogin();
        open("http://localhost:9999");
        SelenideElement body = $("body");
        SelenideElement form = $("form");
        clearAndSendKeys(form.$("[data-test-id=login] input"), username);
        clearAndSendKeys(form.$("[data-test-id=password] input"), getValidPass());
        form.$(By.className("button_theme_alfa-on-white")).click();
        body.$(By.className("paragraph_theme_alfa-on-white")).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Необходимо подтверждение"));
        String authCode = getAuthCode(username);
        clearAndSendKeys(body.$(By.xpath(".//input[@name=\"code\"]")), authCode);
        form.$(By.className("button_theme_alfa-on-white")).click();
        body.$(By.className("heading_theme_alfa-on-white")).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldNotAuthCodeTest() {
        String username = getValidLogin();
        open("http://localhost:9999");
        SelenideElement body = $("body");
        SelenideElement form = $("form");
        clearAndSendKeys(form.$("[data-test-id=login] input"), username);
        clearAndSendKeys(form.$("[data-test-id=password] input"), getValidPass());
        form.$(By.className("button_theme_alfa-on-white")).click();
        body.$(By.className("paragraph_theme_alfa-on-white"))
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));
        String authCode = getInvalidAuth();
        clearAndSendKeys(body.$(By.xpath(".//input[@name=\"code\"]")), authCode);
        body.$(By.className("button_theme_alfa-on-white")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно указан код! Попробуйте ещё раз."));
    }

    @Test
    void shouldNotLoginTest() {
        String username = getValidLogin();
        open("http://localhost:9999");
        SelenideElement body = $("body");
        SelenideElement form = $("form");
        clearAndSendKeys(form.$("[data-test-id=login] input"), username);
        clearAndSendKeys(form.$("[data-test-id=password] input"), getInvalidPass());
        form.$(By.className("button_theme_alfa-on-white")).click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно указан логин или пароль"));
    }

    private void clearAndSendKeys(SelenideElement element, String keysInput) {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        element.setValue(keysInput);
    }
}
