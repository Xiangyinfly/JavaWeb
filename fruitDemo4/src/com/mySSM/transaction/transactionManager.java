package com.mySSM.transaction;

import com.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class transactionManager {
    public static void beginTrans() throws SQLException {
        JDBCUtils.getConnection().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        connection.commit();
        JDBCUtils.closeConnection();
    }

    public static void rollback() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        connection.rollback();
        JDBCUtils.closeConnection();
    }
}
