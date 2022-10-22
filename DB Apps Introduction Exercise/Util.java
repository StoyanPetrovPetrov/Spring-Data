package JavaDBAppsIntroduction;

import JavaDBAppsIntroduction.Constans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

enum Util {
    ;
    static Connection getSqlConnection() throws SQLException {
        final Properties properties = new Properties();
        properties.setProperty(Constans.USER_KEY,Constans.USER_VALUE);
        properties.setProperty(Constans.PASSWORD_KEY, Constans.PASSWORD_VALUE);

        return DriverManager.getConnection(Constans.JDBC_URL,properties);
    }
}
