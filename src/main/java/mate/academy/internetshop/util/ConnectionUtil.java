package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("name", "root");
        dbProperties.put("password", "carlsberg");

        String url = "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t  established to connection to database", e);
        }
    }
}
