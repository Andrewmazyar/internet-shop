package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.internetshop.exception.DataProcessingException;

public class ConnectionUtil {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can`t find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "root");
        dbProperties.put("password", "carlsberg");

        String url = "jdbc:mysql://localhost:3306/internet-shop?serverTimezone=UTC";
        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can`t  established connection to database", e);
        }
    }
}
