package com.kv.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBCConnectionValidator
 *
 * @author KVLT
 * @date 2017-08-14.
 */
public final class JDBCConnectionValidator implements Pool.Validator<Connection> {

    public boolean isValid(Connection conn) {
        if (conn == null) {
            return false;
        }

        try {
            return !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public void invalidate(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {

        }
    }
}
