package com.kv.pool;

import java.sql.Connection;

/**
 * Main
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public class Main {

    private static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String url = "jdbc:sqlserver://localhost:1433;DatabaseName=SER";
    private static final String username = "sa";
    private static final String password = "123456";

    public static void test1() {
        Pool<Connection> pool = new BoundedBlockingPool(10,
                new JDBCConnectionValidator(),
                new JDBCConnectionFactory(driver, url,username,password));
    }

    public static void test2() {
        Pool<Connection> pool = PoolFactory.newBoundedBlockingPool(10,
                new JDBCConnectionFactory(driver, url,username,password),
                new JDBCConnectionValidator());
    }

    public static void main(String[] args) {
        test2();
    }
}
