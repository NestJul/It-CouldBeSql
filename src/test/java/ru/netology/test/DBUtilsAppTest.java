package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.getAuthCode;

public class DBUtilsAppTest {
    @BeforeEach
    public void run() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    public void shouldLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.login(authInfo);
        var verificationPage = new VerificationPage();
        var verificationCode = DataHelper.getVerificationCodeFor(getAuthCode(authInfo));
        verificationPage.verify(verificationCode);
        var dashboardPage = new DashboardPage();
    }

    @Test
    public void shouldNotLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getWrongAuthInfo();
        var errorNotification = loginPage.login(authInfo);
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно указан логин или пароль"));
    }

    @Test
    public void shouldNotAuthCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.login(authInfo);
        var verificationPage = new VerificationPage();
        var verificationCode = DataHelper.getWrongVerificationCodeFor();
        var errorNotification = verificationPage.verify(verificationCode);
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldBe(text("Неверно указан код! Попробуйте ещё раз."));
    }
}
