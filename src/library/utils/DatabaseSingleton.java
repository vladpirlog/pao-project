package library.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import library.interfaces.Closeable;
import library.interfaces.Connectable;

public class DatabaseSingleton implements Connectable, Closeable {
    private static DatabaseSingleton instance = new DatabaseSingleton();

    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private Connection connection;

    private DatabaseSingleton() {
        Properties appProps = new Properties();

        try {
            appProps.load(new FileInputStream(".env"));
        } catch (IOException e) {
            e.printStackTrace();
            DB_URL = System.getenv("PAO_DB_URL");
            DB_USER = System.getenv("PAO_DB_USER");
            DB_PASSWORD = System.getenv("PAO_DB_PASSWORD");
            return;
        }

        DB_URL = appProps.getProperty("PAO_DB_URL");
        DB_USER = appProps.getProperty("PAO_DB_USER");
        DB_PASSWORD = appProps.getProperty("PAO_DB_PASSWORD");
    }

    public static DatabaseSingleton getInstance() {
        return instance;
    }

    @Override
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }
}
