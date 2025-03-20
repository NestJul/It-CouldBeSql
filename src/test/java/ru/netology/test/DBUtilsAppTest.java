package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.getAuthCode;
import static ru.netology.data.SQLHelper.truncateTables;

public class DBUtilsAppTest {
    @BeforeEach
    public void run() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @AfterAll
    static void truncate() {
        truncateTables();
    }

    @Test
    public void shouldLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
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
        loginPage.login(authInfo);
        loginPage.checkErrorNotification();
    }

    @Test
    public void shouldNotAuthCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.login(authInfo);
        var verificationPage = new VerificationPage();
        var verificationCode = DataHelper.getWrongVerificationCodeFor();
        verificationPage.verify(verificationCode);
        verificationPage.checkErrorNotification();
    }
}
