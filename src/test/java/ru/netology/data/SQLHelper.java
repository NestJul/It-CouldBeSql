package ru.netology.data;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {
    private SQLHelper() {}

    private static Connection getConnect() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/app", "app", "pass"
            );
        } catch (SQLException ignored) {}

            return null;
    }

    public static String getAuthCode(String user){
        try {
            Connection connect = getConnect();
            String authUserCodeSQL = "SELECT a.code FROM auth_codes a, users u WHERE u.login = ? AND a.user_id = u.id ORDER BY created DESC LIMIT 1;";
            var dataStmt = connect.prepareStatement(authUserCodeSQL);
            dataStmt.setString(1, user);
            ResultSet resultSet = dataStmt.executeQuery();
            if (resultSet.next()) {
                return  resultSet.getString("code");
            }
        } catch (SQLException ignored) {}

        return "";
    }
}
