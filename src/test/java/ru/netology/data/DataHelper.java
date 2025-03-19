package ru.netology.data;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }
    public static AuthInfo getWrongAuthInfo() {
        return new AuthInfo("vasya", "123qwerty");
    }

    public static VerificationCode getWrongVerificationCodeFor() {
        return new VerificationCode("0000000");
    }

    public static VerificationCode getVerificationCodeFor(String code) {
        return new VerificationCode(code);
    }


    @Value
    public static class VerificationCode {
        private String code;

        public VerificationCode(String code) {
            this.code = code;
        }
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
}