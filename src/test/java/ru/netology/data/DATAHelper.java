package ru.netology.data;

public class DATAHelper {
    private DATAHelper() {}

    public static String getValidLogin() {
        return "vasya";
    }

    public static String getValidPass() {
        return "qwerty123";
    }

    public static String getInvalidPass() {
        return "123qwerty";
    }

    public static String getInvalidAuth() {
        return "000000";
    }
}
