package mytabungan.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {

    public static Connection connect() {
        try {
            Properties props = new Properties();
            InputStream input = DatabaseConfig.class.getClassLoader()
                                .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found in resources!");
            }
            props.load(input);

            String url = props.getProperty("DB_URL");
            String user = props.getProperty("DB_USER");
            String password = props.getProperty("DB_PASSWORD");
            
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}