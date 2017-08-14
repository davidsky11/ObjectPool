package com.kv.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBCConnectionFactory
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public class JDBCConnectionFactory implements ObjectFactory<Connection> {

    private String connectionUrl;
    private String username;
    private String password;

    public JDBCConnectionFactory(String driver, String connectionUrl, String username, String password) {
        super();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ce) {
            throw new IllegalArgumentException("Unable to find driver in classpath", ce);
        }

        this.connectionUrl = connectionUrl;
        this.username = username;
        this.password = password;
    }

    public Connection createNew() {
        try {
            return DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException se) {
            throw new IllegalArgumentException("Unable to create new connection", se);
        }
    }
}
