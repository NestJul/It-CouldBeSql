package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
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

    public static String getAuthCode(DataHelper.AuthInfo authInfo){
        try {
            QueryRunner runner = new QueryRunner();
            Connection connect = getConnect();
            String authUserCodeSQL = "SELECT a.code FROM auth_codes a, users u WHERE u.login = ? AND a.user_id = u.id ORDER BY created DESC LIMIT 1;";
            return runner.query(connect, authUserCodeSQL, new ScalarHandler<>(), authInfo.getLogin());
        } catch (SQLException ignored) {}

        return "";
    }

    public static void truncateTables(){
        try {
            QueryRunner runner = new QueryRunner();
            Connection connect = getConnect();
            String truncateSQL = "SET FOREIGN_KEY_CHECKS = 0;\n" +
                    "TRUNCATE TABLE users;\n" +
                    "TRUNCATE TABLE cards;\n" +
                    "TRUNCATE TABLE auth_codes;\n" +
                    "TRUNCATE TABLE card_transactions;\n" +
                    "SET FOREIGN_KEY_CHECKS = 1;";
            runner.execute(connect, truncateSQL);
        } catch (SQLException ignored) {}
    }
}
