package io.sinq.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.Consumer;

public class DB {
    private String driver;
    private String url;
    private String user;
    private String password;
    private DataSource dataSource;

    private DB(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public DB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void withConnection(Consumer<Connection> consumer) throws Exception {
        try (Connection conn = dataSource != null ? dataSource.getConnection() : DriverManager.getConnection(url, user, password)) {
            consumer.accept(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DB factory(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DB(driver, url, user, password);
    }

    public static DB factory(DataSource dataSource) {
        return new DB(dataSource);
    }
}
